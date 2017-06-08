read processid < pid.txt
echo "Stopping server"
kill $processid
if [ $? -eq 0 ]; then
rm pid.txt
echo "Server stopped"
else
echo "Failed to stop server"
fi
echo "Press any key to exit"
read quit
exit 0