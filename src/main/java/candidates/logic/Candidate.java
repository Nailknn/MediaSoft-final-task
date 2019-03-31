package candidates.logic;

import java.text.DateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Date;
import java.util.Locale;

public class Candidate implements Comparable {
    // поле ид кандидата
    private int candidateId;
    // поле имя
    private String firstName;
    // поле фамилия
    private String surName;
    // поле отчество
    private String patronymic;
    // поле дата рождения
    private Date dateOfBirth;
    // поле пол
    private char sex;
    //номер телефона
    private int phonenumber;
    //номер ид вакансии
    private int jobId;

    public Candidate() {
    }

    public Candidate (ResultSet rs) throws SQLException {
        setCandidateId(rs.getInt(1));
        setFirstName(rs.getString(2));
        setSurName(rs.getString(3));
        setPatronymic(rs.getString(4));
        setDateOfBirth(rs.getDate(5));
        setSex(rs.getString(6).charAt(0));
        setPhonenumber(rs.getInt(7));
        setJobId(rs.getInt(8));
    }

    // get/set для ид кандидата
    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int studentId) {
        this.candidateId = studentId;
    }

    // get/set для имя
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // get/set для фамилии
    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
    //get/set для отчества

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }
    //get/set для ид вакансии
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

        public String toString() {
        return surName + " " + firstName + " " + patronymic + ", "
                + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth)+ ", Компания ID=" + jobId;
    }

    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ru"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }
}
