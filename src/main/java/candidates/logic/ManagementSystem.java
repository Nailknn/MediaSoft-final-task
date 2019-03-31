package candidates.logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManagementSystem {

    private static Connection con;
    private static ManagementSystem instance;

    private ManagementSystem() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/candidates";
            con = DriverManager.getConnection(url, "root", "root");
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static synchronized ManagementSystem getInstance() throws Exception {
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }

    public List<Job> getJobs() throws SQLException {
        List<Job> jobs = new ArrayList<Job>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT job_id, jobName, nameCompany, salary FROM jobs");
            while (rs.next()) {
                Job jb = new Job();
                jb.setJobId(rs.getInt(1));
                jb.setJobName(rs.getString(2));
                jb.setCompanyName(rs.getString(3));
                jb.setSalary(rs.getDouble(4));

                jobs.add(jb);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return jobs;
    }

    public Collection<Candidate> getCandidates() throws SQLException {
        Collection<Candidate> candidates = new ArrayList<Candidate>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(
                    "SELECT candidate_id, firstName, surName, patronymic, " +
                            "dateOfBirth, sex, phonenumber, job_id  FROM candidates " +
                            "ORDER BY surName, firstName, patronymic");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Candidate st = new Candidate(rs);
                candidates.add(st);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return candidates;
    }

    public Collection<Interview> getInterviews() throws SQLException {
        Collection<Interview> interviews = new ArrayList<Interview>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(
                    "SELECT interview_Id, timeInterview, job_Id, candidate_Id," +
                            " result FROM interviews " +
                            "ORDER BY interview_Id, timeInterview, job_Id, candidate_Id,result");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Interview iw = new Interview(rs);
                interviews.add(iw);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return interviews;
    }

    public void insertJob(Job job) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "INSERT INTO jobs " +
                            "(jobName, nameCompany, salary) " +
                            "VALUES (?, ?, ?)");
            stmt.setString(1, job.getJobName());
            stmt.setString(2, job.getCompanyName());
            stmt.setDouble(3, job.getSalary());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertInterview(Interview interview) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "INSERT INTO interviews " +
                            "(interview_Id, timeInterview, job_Id, candidate_Id,result ) " +
                            "VALUES (?, ?, ? ,?, ?)");
            stmt.setInt(1, interview.getInterviewId());
            stmt.setDate(2, new Date(interview.getTimeInterview().getTime()));
            stmt.setInt(3, interview.getJobId());
            stmt.setInt(4, interview.getCandidateId());
            stmt.setBoolean(5, interview.getResult());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertCandidate(Candidate candidate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "INSERT INTO candidates " +
                            "(firstName, patronymic, surName, sex, dateOfBirth,job_id,phonenumber) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, candidate.getFirstName());
            stmt.setString(2, candidate.getPatronymic());
            stmt.setString(3, candidate.getSurName());
            stmt.setString(4, new String(new char[]{candidate.getSex()}));
            stmt.setDate(5, new Date(candidate.getDateOfBirth().getTime()));
            stmt.setInt(6, candidate.getJobId());
            stmt.setInt(7, candidate.getPhonenumber());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void updateCandidate(Candidate candidate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "UPDATE candidates SET " +
                            "firstName=?, patronymic=?, surName=?, " +
                            "sex=?, dateOfBirth=?, job_id=?, phonenumber=? " +
                            "WHERE candidate_id=?");
            stmt.setString(1, candidate.getFirstName());
            stmt.setString(2, candidate.getPatronymic());
            stmt.setString(3, candidate.getSurName());
            stmt.setString(4, new String(new char[]{candidate.getSex()}));
            stmt.setDate(5, new Date(candidate.getDateOfBirth().getTime()));
            stmt.setInt(6, candidate.getJobId());
            stmt.setInt(7, candidate.getPhonenumber());
            stmt.setInt(8, candidate.getCandidateId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void updateInterview(Interview interview) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "UPDATE interviews SET " +
                            "timeInterview=?, job_Id=?, candidate_Id=?, " +
                            "result=? WHERE interview_Id=?");
            stmt.setDate(1, new Date(interview.getTimeInterview().getTime()));
            stmt.setInt(2, interview.getJobId());
            stmt.setInt(3, interview.getCandidateId());
            stmt.setBoolean(4, interview.getResult());
            stmt.setInt(5, interview.getInterviewId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void updateJob(Job job) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "UPDATE jobs SET " +
                            "jobName=?, nameCompany=?, salary=? " +
                            "WHERE job_id=?");
            stmt.setString(1, job.getJobName());
            stmt.setString(2, job.getCompanyName());
            stmt.setDouble(3, job.getSalary());
            stmt.setInt(4, job.getJobId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void deleteCandidate(Candidate candidate) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "DELETE FROM candidates WHERE candidate_id=?");
            stmt.setInt(1, candidate.getCandidateId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void deleteInterview(Interview Interview) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "DELETE FROM interviews WHERE interview_Id=?");
            stmt.setInt(1, Interview.getInterviewId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void deleteJob(Job job) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(
                    "DELETE FROM jobs WHERE job_id=?");
            stmt.setInt(1, job.getJobId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public String getJob(int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String jobName = null;
        try {
            stmt = con.prepareStatement("SELECT jobName FROM jobs WHERE job_id=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            jobName = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return jobName;
    }

    public String getCandidate(int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String candidateName = null;
        try {
            stmt = con.prepareStatement("SELECT surName, firstName FROM candidates WHERE candidate_id=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            candidateName = rs.getString(1) + " " + rs.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return candidateName;
    }
}
