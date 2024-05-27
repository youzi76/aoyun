
import java.util.*;
import java.util.List;

// 运动员类
class Athlete {
    private String username;
    private String name;
    private String nationality;
    private Map<String, List<Double>> results; // 改为项目和成绩的映射

    public Athlete(String username, String name, String nationality) {
        this.username = username;
        this.name = name;
        this.nationality = nationality;
        this.results = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public Map<String, List<Double>> getResults() {
        return results;
    }

    // 添加比赛成绩
    public void addResult(String event, double result) {
        if (!results.containsKey(event)) {
            results.put(event, new ArrayList<>());
        }
        results.get(event).add(result);
    }
}