# Java Dynamic Exe-Graph
- This is a Java implementation of a dynamic execution graph. 
- The graph is built by analyzing the execution of a program and the graph is updated as the program executes. 
- Using Java Debugging Interface (JDI) to monitor the execution of the program and build the graph.
- In this program, feel free to modify the granularity in [StepDebug](src/main/java/org/example/utils/StepDebug.java) to reduce/increase the detail of the graph.
- The graph is visualized using the [Graphviz](https://graphviz.org/) library.

## Version
- 1.1
- Last updated: 2024-03-08
### known issues
- Chinese strings are not supported.
- If the original program has an infinite loop, the program will also have an infinite loop problem.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
- Java 17 or later
- Maven
- Graphviz

### Usage
1. Clone the repository
```shell
  git clone https://github.com/dududuguo/java-dynamic-exe-graph.git
```
2. Create a new black "__input__" file in the root directory of the project
3. Write any input information in the "__input__" file
4. Run Command
```shell
java -jar target/dynamic-exe-graph-1.1.jar <Main class name> <ClassPath>
dot -Tpng graph.dot -o graph.png
```
6. The graph will be generated in the root directory of the project

### Example
```shell
cd java-dynamic-exe-graph
java -jar ./target/dynamic-exe-graph-1.1.jar test.test ./target/classes
dot -Tpng graph.dot -o graph.png
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## More Information
- Repository: [https://github.com/dududuguo/java-dynamic-exe-graph.git](https://github.com/dududuguo/java-dynamic-exe-graph.git)
- The project has used in UTS:48024 Programming 2
- Author: zhihao guo

