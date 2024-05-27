import javax.swing.*;
import java.awt.*;
import java.util.*;

import java.util.List;

public class Main extends JFrame {
    private static Map<String, Athlete> athletes = new HashMap<>();

    public Main() {
        setTitle("运动员管理系统");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton button1 = new JButton("录入运动员信息");
        button1.addActionListener(e -> luru());
        panel.add(button1);

        JButton button2 = new JButton("查询运动员信息(根据用户名）");
        button2.addActionListener(e -> chaxun());
        panel.add(button2);

        JButton button3 = new JButton("查询运动员的成绩");
        button3.addActionListener(e -> chaxun2());
        panel.add(button3);

        JButton button4 = new JButton("查看某个项目的成绩排序（按照成绩排序，显示姓名）");
        button4.addActionListener(e -> paixu());
        panel.add(button4);

        JButton button6 = new JButton("删除运动员信息（根据用户名）");
        button6.addActionListener(e -> shanchu());
        panel.add(button6);

        JButton button7 = new JButton("修改运动员项目成绩（根据用户名和项目）");
        button7.addActionListener(e -> xiugai());
        panel.add(button7);


        JButton button5 = new JButton("退出");
        button5.addActionListener(e -> System.exit(0));
        panel.add(button5);




        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    private static void luru() {
        JFrame frame = new JFrame();
        String username = JOptionPane.showInputDialog(frame, "请输入运动员用户名：");
        if (username == null || username.isEmpty()) {
            return;
        }
        String name = JOptionPane.showInputDialog(frame, "请输入运动员姓名：");
        if (name == null || name.isEmpty()) {
            return;
        }
        String nationality = JOptionPane.showInputDialog(frame, "请输入运动员国籍：");
        if (nationality == null || nationality.isEmpty()) {
            return;
        }
        Athlete athlete = new Athlete(username, name, nationality);

        while (true) {
            String input = JOptionPane.showInputDialog(frame, "请输入运动员参加的比赛项目和成绩（以逗号分隔，例如：100米,10.5），输入\"over\"结束录入：");
            if (input == null || input.equalsIgnoreCase("over")) {
                break;
            }
            String[] parts = input.split(",");
            if (parts.length != 2) {
                JOptionPane.showMessageDialog(frame, "格式错误，请重新输入。");
                continue;
            }
            String event = parts[0].trim();
            double result;
            try {
                result = Double.parseDouble(parts[1].trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "成绩格式错误，请重新输入。");
                continue;
            }
            athlete.addResult(event, result);
        }

        athletes.put(username, athlete);
        JOptionPane.showMessageDialog(frame, "运动员信息录入成功！");
    }

    private static void chaxun() {
        JFrame frame = new JFrame();
        String queryUsername = JOptionPane.showInputDialog(frame, "请输入要查询的运动员用户名：");
        if (queryUsername == null || queryUsername.isEmpty()) {
            return;
        }
        Athlete athlete = athletes.get(queryUsername);
        if (athlete != null) {
            StringBuilder message = new StringBuilder();
            message.append("用户名：").append(athlete.getUsername()).append("\n");
            message.append("姓名：").append(athlete.getName()).append("\n");
            message.append("国籍：").append(athlete.getNationality()).append("\n");
            message.append("比赛成绩：\n");
            for (Map.Entry<String, List<Double>> entry : athlete.getResults().entrySet()) {
                message.append(entry.getKey()).append(": ");
                for (Double result : entry.getValue()) {
                    message.append("(").append(result).append(") ");
                }
                message.append("\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString());
        } else {
            JOptionPane.showMessageDialog(frame, "找不到该运动员。");
        }
    }

    private static void chaxun2() {
        JFrame frame = new JFrame();
        String queryUsername = JOptionPane.showInputDialog(frame, "请输入要查询成绩的运动员用户名：");
        if (queryUsername == null || queryUsername.isEmpty()) {
            return;
        }
        Athlete queryAthlete = athletes.get(queryUsername);
        if (queryAthlete != null) {
            String queryEvent = JOptionPane.showInputDialog(frame, "请输入要查询成绩的项目：");
            if (queryEvent == null || queryEvent.isEmpty()) {
                return;
            }
            List<Double> results = queryAthlete.getResults().get(queryEvent);
            if (results != null) {
                StringBuilder message = new StringBuilder();
                message.append(queryAthlete.getName()).append(" 在 ").append(queryEvent).append(" 项目中的成绩是： ").append(results);
                JOptionPane.showMessageDialog(frame, message.toString());
            } else {
                JOptionPane.showMessageDialog(frame, queryAthlete.getName() + " 没有在 " + queryEvent + " 项目中参加比赛。");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "找不到该运动员。");
        }
    }

    private static void shanchu() {
        JFrame frame = new JFrame();
        String deletename = JOptionPane.showInputDialog(frame, "请输入要删除的运动员用户名：");
        if (deletename == null || deletename.isEmpty()) {
            return;
        }
        Athlete athleteToRemove = athletes.get(deletename);
        if (athleteToRemove != null) {
            athletes.remove(deletename);
            JOptionPane.showMessageDialog(frame, "运动员信息删除成功！");
        } else {
            JOptionPane.showMessageDialog(frame, "找不到该运动员。");
        }
    }

    private static void xiugai() {
        JFrame frame = new JFrame();
        String modifyUsername = JOptionPane.showInputDialog(frame, "请输入要修改成绩的运动员用户名：");
        if (modifyUsername == null || modifyUsername.isEmpty()) {
            return;
        }
        Athlete athleteToModify = athletes.get(modifyUsername);
        if (athleteToModify != null) {
            String modifyEvent = JOptionPane.showInputDialog(frame, "请输入要修改成绩的项目：");
            if (modifyEvent == null || modifyEvent.isEmpty()) {
                return;
            }
            List<Double> results = athleteToModify.getResults().get(modifyEvent);
            if (results != null) {
                String newResultStr = JOptionPane.showInputDialog(frame, "当前成绩为 " + results + "，请输入新成绩：");
                if (newResultStr == null || newResultStr.isEmpty()) {
                    return;
                }
                double newResult;
                try {
                    newResult = Double.parseDouble(newResultStr.trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "成绩格式错误，请重新输入。");
                    return;
                }
                results.clear();
                results.add(newResult);
                JOptionPane.showMessageDialog(frame, "成绩修改成功！");
            } else {
                JOptionPane.showMessageDialog(frame, athleteToModify.getName() + " 没有在 " + modifyEvent + " 项目中参加比赛，无法修改成绩。");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "找不到该运动员。");
        }
    }



    private static void paixu() {
        JFrame frame = new JFrame();
        String queryEvent = JOptionPane.showInputDialog(frame, "请输入要查询成绩的项目：");
        if (queryEvent == null || queryEvent.isEmpty()) {
            return;
        }

        List<Map.Entry<String, Double>> sortedResults = new ArrayList<>();
        for (Athlete athlete : athletes.values()) {
            List<Double> results = athlete.getResults().get(queryEvent);
            if (results != null) {
                String name = athlete.getName();
                for (Double result : results) {
                    sortedResults.add(new AbstractMap.SimpleEntry<>(name, result));
                }
            }
        }
        sortedResults.sort(Comparator.comparing(Map.Entry::getValue));

        StringBuilder message = new StringBuilder();
        message.append("项目 ").append(queryEvent).append(" 的成绩排序（按照成绩排序，显示姓名）：\n");
        for (Map.Entry<String, Double> entry : sortedResults) {
            message.append(entry.getValue()).append(" (").append(entry.getKey()).append(")\n");
        }
        JOptionPane.showMessageDialog(frame, message.toString());
    }
}
