BEGIN {
    FS="|"
}

FILENAME~/ZX00.SGDZ*/ && NF==17 {
    record=$0
    gsub(" ", "", $3)
    gsub("A", "B", $3)
    status[$3]=$15
    FUNDS[$3]=record
}    

FILENAME~/9696_*/ {
    FS=","
    if(NF==16 && $11=="01" ){
        if( ($4 in status) && (status[$4]*$12==1) ){
            mismatch[$4]=$0
        }
    }
}

END {
    print "Mismatch Records:"
    for( orderId in mismatch ){
        print orderId," --- VCP info: ",mismatch[orderId]," --- FUNDS info: ",FUNDS[orderId]
    }
}


