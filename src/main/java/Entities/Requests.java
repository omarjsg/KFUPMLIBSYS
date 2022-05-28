package Entities;

public class Requests {
    private Member member;
    private String name;
    private String requestDate;

    public Requests(Member member, String name, String requestDate) {
        this.member = member;
        this.name = name;
        this.requestDate = requestDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
