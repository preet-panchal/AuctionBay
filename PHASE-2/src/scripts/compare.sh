# Script: compare.sh
# Authors: Francis Hackenberger, Ryan Goulding, Justin Estaris
# Details: Compares the output provided by the test.sh script with
#		   the predetermined expected output files. Returns PASS or
#		   FAIL depending on differences.

#DIRECTORIES
ACTUAL_OUTPUTS=./../testcases/outputs/actual
EXPECTED_OUTPUTS=./../testcases/outputs/expected
DIFFERENCES=./../testcases/outputs/differences

#COLOR CODES
NC='\033[0m';
GRAY='\033[0;37m'
FAIL='\033[0;31m';
PASS='\033[0;32m';

#Remove previous DIFFERENCES files
for folder in $DIFFERENCES/*
do 
	rm $folder/*;
done

#Iterate over all actual output files
for act_folder in $ACTUAL_OUTPUTS/*
do
	for actual_file in $act_folder/*
	do
    #Iterate over all expected output files
	for exp_folder in $EXPECTED_OUTPUTS/*
	do
		for expected_file in $exp_folder/*
		do 
		#Store filenames and test names
		base_actual=$(basename $actual_file)
		base_expected=$(basename $expected_file)
		testname=${base_actual%%.*}
		
		#If actual output file name matches expected output file name
		if [ "${base_actual%%.*}" = "${base_expected%%.*}" ]
		then
			printf "\n${NC}Comparing ${GRAY}$testname"
			foldername=$(basename $exp_folder);
			mkdir -p $DIFFERENCES/$foldername;
			
			#Compare difference between actual output and expected output and place into differences directory
			diff -u $expected_file $actual_file > $DIFFERENCES/$foldername/$testname.txt
			
			#Check if diff command returned true (failed) or false (passed)
			if [ $? -ne 0 ]; then
				printf " --- ${FAIL}FAILED";
			else
				printf " --- ${PASS}PASSED";
			fi
		fi
		done
	done
	done
done