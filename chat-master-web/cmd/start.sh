cd ..
echo "" > chat-master-web.log
nohup pnpm dev > chat-master-web.log &
echo "Start Chat Master Web complete!"
tail -f chat-master-web.log
