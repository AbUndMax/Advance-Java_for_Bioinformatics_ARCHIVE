# Assignment01
### Usage:

Two parameters exist:
- `--filePath / -fp` give this flag the path to the partof_inclusion_relation_list.txt
- `--filter / -f` this flag is **OPTIONAL** and takes any number fo words space seperated

Run the ShowRelationsTree program via mvn:
`mvn exec:java -Dexec.mainClass="assignment01.ShowRelationsTree" -Dexec.args="-fp 
<path/to/partof_inclusion_relation_list.txt> -f <words to filter>" `
