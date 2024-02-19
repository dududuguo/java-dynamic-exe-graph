package org.example.utils;

import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import guru.nidi.graphviz.attribute.Color;
import org.example.utils.Graph.DrawGraph;
import org.example.utils.Graph.NodeSet;
import org.example.utils.Graph.dynamicNode;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;

import java.io.IOError;
import java.util.List;
import java.util.Map;
import java.util.Objects;


// The codes follow the maven project structure:
public class StepDebug {
    private final NodeSet nodeSet;
    private final DrawGraph drawGraph;
    private final String MainPath;
    private dynamicNode prev;
    private int nodeId = 0;
    private int PC = -1;
    private boolean isNormal = false;


    public StepDebug(String mainPath, String OutputPath) {
        nodeSet = new NodeSet();
        drawGraph = new DrawGraph(OutputPath);
        MainPath = mainPath;
        prev = new dynamicNode(0, Color.YELLOW, "Start", "Start");
        prev.setContents("Entry Program.");
        prev.setColor(Color.BROWN);
    }

    public void start() {

        // end node
        dynamicNode endNode = new dynamicNode(-1, Color.BLUE, "End", "End");
        boolean done = false;
        dynamicNode cur;

        try {
            // Create a connector to the JVM
            VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
            LaunchingConnector launchingConnector = vmm.defaultConnector();
            Map<String, Connector.Argument> env = launchingConnector.defaultArguments();
            env.get("main").setValue(MainPath);  // set the main class
            // TODO: fix your the classpath
            env.get("options").setValue("-classpath /home/classes");

            // setup the JVM and launch the program
            VirtualMachine vm = launchingConnector.launch(env);

            // set a method entry breakpoint
            EventRequestManager erm = vm.eventRequestManager();
            // create a class prepare request
            ClassPrepareRequest classPrepareRequest = erm.createClassPrepareRequest();
            classPrepareRequest.addClassFilter("*");
            // set the class prepare request to enable
            classPrepareRequest.enable();

            // get the event queue of the VM
            EventQueue eventQueue = vm.eventQueue();
            // delete all breakpoints
            erm.deleteAllBreakpoints();

            System.out.println("Start debugging");
            while (!done) {
                EventSet eventSet = eventQueue.remove();
                for (Event event : eventSet) {
                    if (event instanceof VMDeathEvent || event instanceof VMDisconnectEvent) {
                        done = true;
                        isNormal = true;
                        break;
                    } else if (event instanceof ClassPrepareEvent classPrepareEvent) {
                        ReferenceType refType = classPrepareEvent.referenceType();
                        List<Method> methods = refType.methodsByName("main");

                        if (!methods.isEmpty()) {
                            Method method = methods.get(0);
                            Location location = method.location();  // get the location of the main method
                            BreakpointRequest bpReq = erm.createBreakpointRequest(location);
                            bpReq.enable(); // 启用断点
                            prev.setPC("Line: " + location.lineNumber() + (++PC));

                        }
                    } else if (event instanceof BreakpointEvent breakpointEvent) {
                        // deal with the breakpoint event
                        ThreadReference threadRef = breakpointEvent.thread();
                        StepRequest stepRequest = erm.createStepRequest(
                                threadRef,
                                StepRequest.STEP_LINE,
                                StepRequest.STEP_INTO
                        );
                        stepRequest.addClassExclusionFilter("java.*");
                        stepRequest.addClassExclusionFilter("javax.*");
                        stepRequest.addClassExclusionFilter("sun.*");
                        stepRequest.addClassExclusionFilter("jdk.*");
                        stepRequest.addClassExclusionFilter("In");
                        stepRequest.enable();
//                        System.out.println("Breakpoint at " + breakpointEvent.location());

                    } else if (event instanceof StepEvent se) {
                        // deal with the step event
                        Location loc = se.location();
                        Method method = loc.method();

                        int lineNumber = loc.lineNumber();
                        StringBuilder content = new StringBuilder();
                        content.append(method).append("\n");

                        StringBuilder contents = new StringBuilder();

                        PC++;
                        nodeId++;

                        try {
                            StackFrame frame = se.thread().frame(0);
                            if (frame == null) {
                                continue;
                            }

                            StringBuilder temp = new StringBuilder();
                            for (LocalVariable var : frame.visibleVariables()) {
                                Value value = frame.getValue(var);
                                if (value == null ||
                                        value.type().name().equals("java.util.Scanner") ||
                                        value.type().name().equals("java.io.FileInputStream") ||
                                        value.type().name().equals("java.io.PrintStream") ||
                                        value.type().name().equals("java.lang.String[] args")
                                ) {
                                    continue;
                                }
                                try {
                                    int intVal = Integer.parseInt(value.toString());
                                    temp.append(value.type().name()).append(" ").append(var.name()).append(" = ").append(intVal).append("\n");
                                } catch (NumberFormatException e) {
                                    temp.append(value.type().name()).append(" ").append(var.name()).append(" = ").append(value).append("\n");
                                }
                            }
                            if (!temp.isEmpty()) {
                                content.append("Local Variables: ").append(temp);
                            }
                        } catch (IOError e) {
                            System.out.println("Debugging information not available.");
                        }

                        contents.append(content);

                        cur = new dynamicNode(nodeId, Color.BLACK, content.toString(), PC + "\nLine: " + lineNumber);
                        cur.setContents(contents.toString());

                        if (Objects.equals(prev.getContent(), content.toString())) {
                            nodeId--;
                            PC--;
                            continue;
                        }
//                        System.out.println(content.toString());
                        if (!nodeSet.getNode(content.toString())) {
                            nodeSet.addNode(content.toString(), nodeId);
                        } else {
                            nodeId--;
                            int oldID = nodeSet.findID(content.toString());
                            cur.setNodeId(oldID);
                            cur.setColor(Color.RED);
                        }

                        drawGraph.draw(prev, cur);
                        prev = cur;
                    }
                }
                eventSet.resume();
            }

        } catch (Exception e) {
            System.out.println("Exception occurred " + e.getMessage());
        }

        endNode.setPC(String.valueOf(++PC));
        endNode.setNodeId(++nodeId);
        if (!isNormal) {
            endNode.setContent("The program is core dump, please check the code.");
            endNode.setLabel("Core Dump");

            prev.setColor(Color.GREEN);
            drawGraph.draw(prev, endNode);
            drawGraph.drawLast(endNode);
        } else {
            endNode.setLabel(PC + " Normal End");
            endNode.setContents("Program Normal End !");

            prev.setColor(Color.BLUE);
            drawGraph.draw(prev, endNode);
            drawGraph.drawLast(endNode);
        }

        drawGraph.outputFile();

        System.out.println("End debugging");
    }
}

