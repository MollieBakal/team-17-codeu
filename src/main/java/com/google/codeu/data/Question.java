import Message;
import java.util.List;

public class Question extends Message{
    private List<Message> answers = new ArrayList<>();
    private String access = "public";
    
    public Question(String user, String text) {
        super(user, text);
    }
    
    public Question(UUID id, String user, String text, long timestamp, String access) {
        super(id, user, text, timestamp);
        this.access = access;
    }
    
    public List<Message> getAnswers(){
        return answers;
    }
    
    public void addAnswer(Message answer){
        this.answers.add(answer);
    }
    
    public String getList(){
        return access;
    }
    
    public void setList(String stuff){
        this.access = stuff;
    }
}

