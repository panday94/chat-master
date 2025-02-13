cd ..
echo "" > chat-master-admin.log
nohup pnpm dev > chat-master-admin.log &
echo "Start Chat Master Admin complete!"
tail -f chat-master-admin.log
