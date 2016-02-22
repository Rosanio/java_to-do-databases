import java.util.*;
import org.sql2o.*;

public class Task {
  private int id;
  private String description;

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getID() == newTask.getID();
    }
  }

  public int getID() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Task(String description) {
    this.description = description;
  }

  public static List<Task>  all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO tasks (description) values (:description)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
      .addParameter("description", description)
      .executeUpdate()
      .getKey();
    }
  }

  public static Task find(int taskID) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks WHERE id = :ID";
      return con.createQuery(sql)
      .addParameter("ID", taskID)
      .executeAndFetchFirst(Task.class);
    }
  }
}
