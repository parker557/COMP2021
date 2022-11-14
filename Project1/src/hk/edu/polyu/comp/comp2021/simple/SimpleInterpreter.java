package hk.edu.polyu.comp.comp2021.simple;

import hk.edu.polyu.comp.comp2021.simple.model.Simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SimpleInterpreter {
    static HashMap<String, Vardef> allVardef  = new HashMap<>();
    static  HashMap<String, Binexpr> allBexpr = new HashMap<>();
    static  HashMap<String, Print> allPrint = new HashMap<>();
    static HashMap<String, Skip> allSkip = new HashMap<>();
    static  HashMap<String, If> allIf = new HashMap<>();
    static  HashMap<String, Unexpr>allUnexpr=new HashMap<>();
    static  HashMap<String, Assign>allAssign=new HashMap<>();
    static  HashMap<String, Loop>allLoop=new HashMap<>();
    static  HashMap<String, Block>allBlock=new HashMap<>();
    public static void main(String[] args) {

        boolean flag=true;
        while(flag){
            Scanner sc = new Scanner(System.in);
            String cmd = sc.nextLine();
            String[] cmdArr = cmd.split(" ");
            System.out.println(cmdArr[0]);
            switch (cmdArr[0]){
                case "vardef":
                    SimpleInterpreter.Vardef vardef = new SimpleInterpreter.Vardef(cmdArr[1],cmdArr[2],cmdArr[3],cmdArr[4]);
                    allVardef.put(cmdArr[1], vardef);
                    System.out.println("@ "+vardef.lab+":"+vardef.typ+"" +vardef.varName+"="+cmdArr[4]);
                    break;
                case "binexpr":
                    SimpleInterpreter.Binexpr bexpr = new SimpleInterpreter.Binexpr(cmdArr[1],cmdArr[2],cmdArr[3],cmdArr[4]);
                    allBexpr.put(cmdArr[1],bexpr);
                    System.out.println("@ "+bexpr.expName+":"+bexpr.expRef1+" "+bexpr.bop+" "+bexpr.expRef2);
                    break;
                case "print":
                    SimpleInterpreter.Print print = new SimpleInterpreter.Print(cmdArr[1],cmdArr[2]);
                    allPrint.put(cmdArr[1],print);
                    System.out.println("@ "+print.lab+":"+print.expRef);
                    break;
                case "skip":
                    SimpleInterpreter.Skip skip = new SimpleInterpreter.Skip(cmdArr[1]);
                    allSkip.put(cmdArr[1],skip);
                    System.out.println("@ "+skip.lab+":skip");
                    break;
                case "if":
                    SimpleInterpreter.If if1 = new SimpleInterpreter.If(cmdArr[1],cmdArr[2],cmdArr[3],cmdArr[4]);
                    allIf.put(cmdArr[1],if1);
                    System.out.println("@ "+if1.lab+" "+if1.expRef+" "+if1.statementLab1+" "+if1.statementLab2);
                    break;
                case "unexpr":
                    SimpleInterpreter.Unexpr unexpr=new SimpleInterpreter.Unexpr(cmdArr[1],cmdArr[2],cmdArr[3]);
                    allUnexpr.put(cmdArr[1],unexpr);
                    System.out.println("@"+unexpr.expName+" "+unexpr.uop+" "+unexpr.expRef);
                    break;
                case "assign":
                    SimpleInterpreter.Assign assign=new SimpleInterpreter.Assign(cmdArr[1],cmdArr[2],cmdArr[3]);
                    allAssign.put(cmdArr[1],assign);
                    System.out.println("@"+assign.lab+" "+assign.varName+" "+assign.expRef);
                    break;
                case "while":
                    SimpleInterpreter.Loop loop=new SimpleInterpreter.Loop(cmdArr[1],cmdArr[2],cmdArr[3]);
                    allLoop.put(cmdArr[1],loop);
                    System.out.println("@"+loop.lab+" "+loop.expRef+" "+loop.statement);
                    break;
                case "block":
                    SimpleInterpreter.Block block=new SimpleInterpreter.Block(cmdArr[1],cmdArr[2]);
                    int length=cmdArr.length-3;
                    for (int i=0;i<length;i++){
                        block.statementlabs.add(cmdArr[i+3]);
                    }
                    allBlock.put(cmdArr[1],block);
                    System.out.println("@"+block.lab+" "+block.statementlabs.toString());
                    break;
                case "quit":
                    flag=false;
                    break;
                case "program":
                    Program program = new Program(cmdArr[1],cmdArr[2]);
                    break;
                default:
                    System.out.println("cmd error");
                    flag=false;
                    break;
            }

        }
    }

    static class Program{
        String programName;
        String statementLab;
        public Program(String programName,String statementLab) {
            this.statementLab = statementLab;
            Block block = allBlock.get(statementLab);
            this.programName = programName;
            ArrayList<String> statementlabs = block.getStatementlabs();
            for (String statementlab : statementlabs) {
                String cmd = statementlab.substring(0,statementlab.length()-1);
                System.out.println(cmd);
            }
        }
    }



    static  class Vardef{
        String lab;
        String typ;

        String varName;
        String expRef;

        public Vardef(String lab, String typ,String varName, String expRef) {
            this.lab = lab;
            this.typ = typ;
            this.varName = varName;
            this.expRef = expRef;
        }
    }

    static class Binexpr{
        String expName;
        String expRef1;
        String expRef2;
        String bop;

        public Binexpr(String expName,String expRef1, String bop, String expRef2) {
            this.expName = expName;
            this.expRef1 = expRef1;
            this.expRef2 = expRef2;
            this.bop = bop;
        }
    }

    static class Print{
        String lab;
        String expRef;

        public Print(String name, String expRef) {
            this.lab = name;
            this.expRef = expRef;
        }
    }
    static class Skip{
        String lab;


        public Skip(String lab) {
            this.lab = lab;

        }
    }

    static class If{
        String lab;
        String expRef;
        String statementLab1;
        String statementLab2;

        public If(String name, String expRef, String statement1, String statement2) {
            this.lab = name;
            this.expRef = expRef;
            this.statementLab1 = statement1;
            this.statementLab2 = statement2;
        }
    }

    static class Unexpr{
        String expName;
        String uop;
        String expRef;
        public Unexpr(String expName,String uop,String expRef){
            this.expName=expName;
            this.uop=uop;
            this.expRef=expRef;
        }

    }

    static class Assign{
        String lab;
        String varName;
        String expRef;
        public Assign(String lab,String varName,String expRef){
            this.lab=lab;
            this.varName=varName;
            this.expRef=expRef;
        }
    }

    static class Loop{
        String lab;
        String statement;
        String expRef;
        public Loop(String lab,String expRef,String statement) {
            this.lab = lab;
            this.statement = statement;
            this.expRef = expRef;
        }
    }

    static class Block{
        String lab;
        ArrayList<String>statementlabs=new ArrayList<>();
        public Block(String lab,String statementlab){
            this.lab=lab;
            statementlabs.add(statementlab);
        }

        public String getLab() {
            return lab;
        }

        public ArrayList<String> getStatementlabs() {
            return statementlabs;
        }
    }
}

