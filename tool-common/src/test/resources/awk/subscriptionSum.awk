BEGIN {
    FS="|"
    sum=0
}

FILENAME~/ZX00.SGDZ*/ && NF==17 {
    if( $5 != 104 && $15==0){
        sum=sum+$8
    }
}    

FILENAME~/9696_*/ {
    FS=","
    if(NF==16){
        if( ($4 in status) && (status[$4]*$12==1) ){
            mismatch[$4]=$0
        }
    }
}

END {
    printf "Total amount from other bank: %.2f \n",sum
}


