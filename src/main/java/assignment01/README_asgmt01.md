# Assignment01
### Usage:

Two parameters exist:
- `--filePath / -fp` give this flag the path to the partof_inclusion_relation_list.txt
- `--filter / -f` this flag is **OPTIONAL** and takes any number fo words space seperated

Built and compile the mvn project from the project folder:
`mvn clean compile`

Run the ShowRelationsTree program via mvn in the console from the project folder:
`mvn exec:java -Dexec.mainClass="assignment01.ShowRelationsTree" -Dexec.args="-fp 
<path/to/partof_inclusion_relation_list.txt> -f <words to filter>" `
 
Even with a  `module-info.java`file I was not able to "shortcut" the program call!
Maybe we can explain this a little bit better in detail in the next tutorial :)