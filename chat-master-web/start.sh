cd ..
echo "" > web.log
nohup pnpm dev > web.log &
echo "Start Chat Master Web complete!"
tail -f web.log
