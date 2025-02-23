# VSCode extensions 

# Wait for code server to be ready 
until [ -f /home/ost/.local/bin/code-server ] 
do 
  sleep 1 
done 

code-server --install-extension haskell.haskell
