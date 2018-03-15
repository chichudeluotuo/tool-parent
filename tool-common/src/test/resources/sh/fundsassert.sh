#!/bin/bash
#  1.生成每天增量卡信息文件

shellPath=$(pwd)
cd ./ezdb-batchs-service/bin
echo "[INFO]: -------------------------生成新增卡信息表开始, 时间------------------------" `date +%Y%m%d_%H:%M:%S`
sh ./propertyReportBootStrap.sh
echo "[INFO]: ------------------------生成新增卡信息表结束, 时间：-----------------------" `date +%Y%m%d_%H:%M:%S`

#  2.把文件追加到历史全量卡信息表中

#加载constants.properties文件[GPSharePath=/ezdb/share/]
. ./constants.properties

#打印constants.properties文件中key值
echo $GPSharePath
gpSharePath=$GPSharePath

#存放funds发送过来的份额对账文件ZX00.FEDZ.xxx.xxx.txt目录
tdgSourceFilePath=$gpSharePath"ezdb_file/"
dataDealPath=$gpSharePath"ezdb-datadeal/fundsAssert/"

#存放goodsinfo.properties目录
currentFilePath=$dataDealPath"current/"

#存放ezdbAllCardInfo.txt文件的目录
stockFilePath=$dataDealPath"stock/"

#备份（按照日期）份额对账文件目录
historyFilePath=$dataDealPath"history/"
destFilePath=$dataDealPath"temp/"
reportPath=$destFilePath

#保有资产报表压缩文件存放目录
zipReportPath=$gpSharePath"ezdb-reportdownload/fundsAssert/"
stockFileName="ezdbAllCardInfo.txt"

#本地生成新增卡信息文件
currentFileName="currentNewCardInfo.txt"

#加载goodsinfo.properties
. $currentFilePath"goodsinfo.properties"

#基金公司代码和名称
funds_no_arr=($FUNDS_NO_ARR)
funds_name_arr=($FUNDS_NAME_ARR)

#基金产品代码和名称
goods_no_arr=($GOODS_NO_ARR)
goods_name_arr=($GOODS_NAME_ARR)
filenames=""

##获取前一天时间
currentDate=$(date +"%Y%m%d" -d "-1 days")

##创建必要的目录

if [ ! -d $currentFilePath ]; then
   echo "创建目录"$currentFilePath
   mkdir $currentFilePath
fi

if [ ! -d $historyFilePath ]; then
   echo "创建目录"$historyFilePath
   mkdir $historyFilePath
fi

if [ ! -d $destFilePath ]; then
   echo "创建目录"$destFilePath
   mkdir $destFilePath
fi

if [ ! -d $zipReportPath ]; then
   echo "创建目录"$zipReportPath
   mkdir $zipReportPath
fi
echo "[INFO]:------------------------- 将新增卡信息表合并到历史存量卡信息表中开始, 时间：-------------------------" `date +%Y%m%d_%H:%M:%S`
if [ -f $currentFilePath$currentFileName ]; then
    echo "[INFO]:合并新增卡信息到全量文件中,临时文件："$currentFilePath$currentFileName"；全量卡信息文件；"$stockFilePath$stockFileName
    cat $currentFilePath$currentFileName >> $stockFilePath$stockFileName
    rm -rf $currentFilePath$currentFileName
else
    echo "[INFO]:"$currentFilePath$currentFileName"文件不存在"
fi
echo "[INFO]: 文件合并执行完毕, 时间：" `date +%Y%m%d_%H:%M:%S`
echo "[INFO]:------------------------- 将新增卡信息表合并到历史存量卡信息表中结束, 时间：-------------------------" `date +%Y%m%d_%H:%M:%S`
echo
echo "[INFO]:------------------------- 报表生成开始, 时间：-------------------------" `date +%Y%m%d_%H:%M:%S`
for((i=0;i<${#funds_no_arr[@]};i++))
do
    echo "-----------当前正在处理的基金产品公司代码为:"${funds_no_arr[i]} " 开始------------"

	#funds推送文件
    fileName="ZX00.FEDZ."${funds_no_arr[i]}"."$currentDate".txt"
	#生成报表文件名、临时文件名、压缩文件名、报表第一行信息
    reportName="EZDB-ASSERT-"${funds_no_arr[i]}"."$currentDate".txt"
	reportTempName="EZDB-ASSERT-TEMP"${funds_no_arr[i]}"."$currentDate".txt"
    zipReportName="EZDB-ASSERT-"${funds_no_arr[i]}"."$currentDate".zip"
	reportTitle=${funds_no_arr[i]}"-"${funds_name_arr[i]}-$currentDate
###############################################################################
###  3.funds资产确认文件数据提取
###############################################################################
	if [ ! -f $tdgSourceFilePath$fileName ]; then
		echo $tdgSourceFilePath$fileName
        echo "[WARN]:文件："$tdgSourceFilePath$fileName" 不存在，继续处理下一个文件"
        echo
        continue
     fi
     echo "文件："$tdgSourceFilePath$fileName" 存在，处理数据，生成报表"
	 if [ ! -d $historyFilePath$currentDate ]; then
	 #创建本分文件目录
        echo "创建目录"$historyFilePath$currentDate
        mkdir $historyFilePath$currentDate
	 fi
        cp $tdgSourceFilePath$fileName $historyFilePath$currentDate
	echo "去文件中的空格"
        sed -i s/[[:space:]]//g $tdgSourceFilePath$fileName 
         #sed -i '1d' $tdgSourceFilePath$fileName 
###############################################################################
###  3.1.start funds资产确认文件数据筛选，将属于养老宝的数据删掉
###############################################################################	 
	for((j=0;j<${#goods_no_arr[@]};j++))
	do
		#将数据写入临时文件中
		grep ${goods_no_arr[j]} $tdgSourceFilePath$fileName >> $tdgSourceFile"temp.txt"	
	done
	rm -rf $tdgSourceFilePath$fileName
	#修改临时文件名
	mv $tdgSourceFile"temp.txt" $tdgSourceFilePath$fileName
###############################################################################
###  3.1.end funds资产确认文件数据筛选，将属于养老宝的数据删掉
###############################################################################	 
     awk -F '|' '{print $6"|"$7"|"$8"|"$14}'  $tdgSourceFilePath$fileName > $destFilePath$fileName
     rm -rf $tdgSourceFilePath$fileName 
     echo "[INFO]: funds资产确认文件数据提取执行完毕, 时间：" `date +%Y%m%d_%H:%M:%S`
################################################################################
###  4.funds资产确认文件与卡全量信息表合并
################################################################################
	 touch $reportPath$reportName
	 touch $reportPath$reportTempName
	 echo $reportTitle >> $reportPath$reportName
	 echo "客户所属机构码$|$|用户id$|$|核心客户号$|$|银行卡号$|$|发卡行$|$|发卡行机构号$|$|发卡行上级机构号$|$|发卡行省行机构号$|$|产品代码$|$|时点资产余额" >> $reportPath$reportName
         awk -F '|' 'NR==FNR{a[$1]=$2"|"$3"|"$4"|"$5"|"$6"|"$7} NR!=FNR {print $1"|"$2"|"$3"|"$4"|"a[$1]}'  $stockFilePath$stockFileName $destFilePath$fileName > $reportPath$reportTempName
         awk -F '|' '{print $10"$|$|"$8"$|$|"$9"$|$|"$1"$|$|"$2"$|$|"$5"$|$|"$6"$|$|"$7"$|$|"$3"$|$|"$4}' $reportPath$reportTempName >> $reportPath$reportName
	 echo "[INFO]: funds资产确认文件与卡全量信息表合并执行完毕, 时间：" `date +%Y%m%d_%H:%M:%S` 
	 filenames=$filenames","$zipReportName
	 rm -rf $destFilePath$fileName
	 rm -rf $reportPath$reportTempName
################################################################################
###  5.压缩报表到报表下周目录
################################################################################
     cd $reportPath
     tar -zcvf $zipReportName $reportName
	 if [ ! -d $zipReportPath$currentDate ]; then
        echo "创建目录"$zipReportPath$currentDate
        mkdir $zipReportPath$currentDate
	 fi
	 cp $zipReportName $zipReportPath$currentDate
	 rm -rf $reportName
	 rm -rf $zipReportName
     echo "---------当前正在处理的基金产品公司代码为:"$i" 结束------------"
     echo
     echo
done
echo "[INFO]:------------------------- 报表生成结束, 时间：-------------------------" `date +%Y%m%d_%H:%M:%S`
echo
################################################################################
###  6.报表信息入库，供后台管理端下载
################################################################################
nullString=""
if [ "$filenames"x = "$nullString"x ]; then
	echo "[INFO]:------------------------------当天没有报表生成end-------------------------------------"`date +%Y%m%d_%H:%M:%S`
else
    echo "[INFO]: ----------------------------------------报表信息入库开始, 时间：-------------------------------------" `date +%Y%m%d_%H:%M:%S` 
    cd $shellPath"/ezdb-batchs-service/bin"
    echo "[INFO]: 资产确认报表已经生产完毕，将报表路径信息入库, 时间：" `date +%Y%m%d_%H:%M:%S` 
    sh ./fundsPropertyReportInsertDB.sh $filenames $zipReportPath$currentDate"/"
    echo "[INFO]: ----------------------------------------入库完成，可以下载, 时间：-------------------------------------" `date +%Y%m%d_%H:%M:%S` 
fi



