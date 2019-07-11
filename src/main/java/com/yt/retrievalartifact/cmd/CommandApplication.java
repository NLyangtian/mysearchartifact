package com.yt.retrievalartifact.cmd;

import com.yt.retrievalartifact.core.EverythingMannager;
import com.yt.retrievalartifact.core.model.Condition;

import java.sql.SQLOutput;
import java.util.Scanner;

public class CommandApplication {
    public static void main(String[] args) {


        //1.EverythingManager
        EverythingMannager mannager=EverythingMannager.getInstance();
        //2.命令行交互
        Scanner scanner=new Scanner(System.in);
        //3.用户交互输入
        System.out.println(" --- 欢迎使用搜索神器！--- ");
        while(true) {
            System.out.print(">>");
            String line = scanner.nextLine();
            switch (line) {
                case "help":
                    mannager.help();
                    break;
                case "quit":
                    mannager.quit();
                case "index":
                    mannager.buildIndex();
                    break;
                default:{
                    if(line.startsWith("search")){
                        //解析参数

                        String[] segements=line.split(" ");
                        if(segements.length>=2) {
                            Condition condition=new Condition();
                            String name = segements[1];
                            if (segements.length >= 3) {
                              String type = segements[2];
                              condition.setFileType(type.toUpperCase());
                            }
                            mannager.search(condition).forEach(things -> {
                                System.out.println(things.getPath());
                            });
                        }else{
                            mannager.help();
                        }
                    }else{
                        mannager.help();
                    }
                }
            }
        }
    }
}
