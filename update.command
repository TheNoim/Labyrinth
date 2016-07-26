cd /Users/privat/Documents/NTA2016/Labyrinth
clear
echo -e "\033[0;31;1mBITTE WARTEN...\033[0m"
echo
echo -e "\033[1;33mDas Starten des Vorganges kann einen Moment dauern."
echo -e "Bitte haben Sie ein wenig Gedult.\033[0m"
echo
echo
echo -e "\033[0;37m"
git pull
git add .
git commit -m "janniswiehart updated project \"Labyrinth\""
git push -u origin master
echo
echo
echo -e "\033[0;32;1mFERTIG\033[0m"
echo
sleep 1
exit
