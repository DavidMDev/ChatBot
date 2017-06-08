read -p "Do you wish to compile the server before starting? <y/n>" prompt
if [[ $prompt == "y" || $prompt == "Y" || $prompt == "yes" || $prompt == "Yes" ]]
then
echo "Building server"
./gradlew -Pprod=true
fi
echo "Starting server"
cp settings.xml ./build/libs/settings.xml
cd build/libs/
echo  "moved to " $(pwd)
nohup java -jar project-0.0.1-SNAPSHOT.jar > /dev/null &
sleep 5
processid=$!
cd ..
cd ..
FILE=pid.txt
if [[ -s $FILE ]] ; then
echo "Server is already running"
else
echo $processid > pid.txt
echo "Server started"
fi ;

echo "press any key to exit"
read quit
exit 0
