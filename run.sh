find . -type f -name "*.java" > java_files.txt
javac @java_files.txt -d bin
java -cp bin Game