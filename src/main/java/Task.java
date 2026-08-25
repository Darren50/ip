public class Task {
    private String description;
    private boolean isDone;


    public Task(String description){
        this.description = description;
        this.isDone = false;
    }

    public void setAsDone(){
        this.isDone = true;
    }

    public void setAsNotDone(){
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon(){
        if (isDone()){
            return "[X]";
        } else{
            return "[ ]";
        }
    }
    public boolean isDone() {
        return isDone;
    }
}
