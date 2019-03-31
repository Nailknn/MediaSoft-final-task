package candidates.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Interview {

    private int interviewId;
    private Date timeInterview;
    private int jobId;
    private int candidate;
    private boolean result;

    public Interview() {
    }

    public Interview (ResultSet rs) throws SQLException {
        setInterviewId(rs.getInt(1));
        setTimeInterview(rs.getDate(2));
        setJobId(rs.getInt(3));
        setCandidate(rs.getInt(4));
        setResult(rs.getBoolean(5));
    }

    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Date getTimeInterview() {
        return timeInterview;
    }

    public void setTimeInterview(Date timeInterview) {
        this.timeInterview = timeInterview;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getCandidateId() {
        return candidate;
    }

    public void setCandidate(Integer candidate) {
        this.candidate = candidate;
    }
}
