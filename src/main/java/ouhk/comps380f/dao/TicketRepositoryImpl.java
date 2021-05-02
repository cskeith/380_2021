package ouhk.comps380f.dao;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Ticket;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public TicketRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class TicketExtractor implements ResultSetExtractor<List<Ticket>> {

        @Override
        public List<Ticket> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Ticket> map = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                Ticket ticket = map.get(id);
                if (ticket == null) {
                    ticket = new Ticket();
                    ticket.setId(id);
                    ticket.setCustomerName(rs.getString("name"));
                    ticket.setSubject(rs.getString("subject"));
                    ticket.setBody(rs.getString("body"));
                    map.put(id, ticket);
                }
                String filename = rs.getString("filename");
                if (filename != null) {
                    Attachment attachment = new Attachment();
                    attachment.setName(rs.getString("filename"));
                    attachment.setMimeContentType(rs.getString("content_type"));
                    attachment.setTicketId(id);
                    ticket.addAttachment(attachment);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public long createTicket(final String customerName, final String subject,
            final String body, List<MultipartFile> attachments)
            throws IOException {
        final String SQL_INSERT_TICKET
                = "insert into ticket (name, subject, body) values (?, ?, ?)";
        final String SQL_INSERT_ATTACHMENT
                = "insert into attachment (filename, content, content_type,"
                + " ticket_id) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_TICKET,
                        new String[]{"id"});
                ps.setString(1, customerName);
                ps.setString(2, subject);
                ps.setString(3, body);
                return ps;
            }
        }, keyHolder);

        Long ticket_id = keyHolder.getKey().longValue();
        System.out.println("Ticket " + ticket_id + " inserted");

        for (MultipartFile filePart : attachments) {
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                LobHandler handler = new DefaultLobHandler();
                jdbcOp.update(SQL_INSERT_ATTACHMENT,
                        new Object[]{filePart.getOriginalFilename(),
                            new SqlLobValue(filePart.getInputStream(),
                                    (int) filePart.getSize(), handler),
                            filePart.getContentType(),
                            ticket_id},
                        new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR,
                            Types.INTEGER});
                System.out.println("Attachment " + filePart.getOriginalFilename()
                        + " of Ticket " + ticket_id + " inserted");
            }
        }
        return ticket_id;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getTickets() {
        final String SQL_SELECT_TICKETS = "select t.*, a.filename, a.content_type,"
                + " a.content from ticket as t left join attachment as a"
                + " on t.id = a.ticket_id";
        return jdbcOp.query(SQL_SELECT_TICKETS, new TicketExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getTicket(long id) {
        final String SQL_SELECT_TICKET = "select t.*, a.filename, a.content_type,"
                + " a.content from ticket as t left join attachment as a"
                + " on t.id = a.ticket_id where t.id = ?";
        return jdbcOp.query(SQL_SELECT_TICKET, new TicketExtractor(), id);
    }

    @Override
    @Transactional
    public void updateTicket(long ticket_id, String subject, String body,
            List<MultipartFile> attachments) throws IOException {
        final String SQL_UPDATE_TICKET = "update ticket set subject=?, body=? where id=?";
        final String SQL_INSERT_ATTACHMENT
                = "insert into attachment (filename,content,content_type,ticket_id) values (?,?,?,?)";

        jdbcOp.update(SQL_UPDATE_TICKET, subject, body, ticket_id);
        System.out.println("Ticket " + ticket_id + " updated");

        for (MultipartFile filePart : attachments) {
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                LobHandler handler = new DefaultLobHandler();
                jdbcOp.update(SQL_INSERT_ATTACHMENT,
                        new Object[]{filePart.getOriginalFilename(),
                            new SqlLobValue(filePart.getInputStream(),
                                    (int) filePart.getSize(), handler),
                            filePart.getContentType(),
                            ticket_id},
                        new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.INTEGER});
                System.out.println("Attachment " + filePart.getOriginalFilename()
                        + " of Ticket " + ticket_id + " inserted");
            }
        }
    }

    @Override
    @Transactional
    public void deleteTicket(long id) {
        final String SQL_DELETE_TICKET = "delete from ticket where id=?";
        final String SQL_DELETE_ATTACHMENTS = "delete from attachment where ticket_id=?";
        jdbcOp.update(SQL_DELETE_ATTACHMENTS, id);
        jdbcOp.update(SQL_DELETE_TICKET, id);
        System.out.println("Ticket " + id + " deleted");
    }

    @Override
    @Transactional
    public void deleteAttachment(long ticketId, String name) {
        final String SQL_DELETE_ATTACHMENT
                = "delete from attachment where ticket_id=? and filename=?";
        jdbcOp.update(SQL_DELETE_ATTACHMENT, ticketId, name);
        System.out.println("Attachment " + name + " of Ticket " + ticketId + " deleted");
    }

    private static final class AttachmentRowMapper implements RowMapper<Attachment> {

        @Override
        public Attachment mapRow(ResultSet rs, int i) throws SQLException {
            Attachment entry = new Attachment();
            entry.setName(rs.getString("filename"));
            entry.setMimeContentType(rs.getString("content_type"));
            Blob blob = rs.getBlob("content");
            byte[] bytes = blob.getBytes(1l, (int) blob.length());
            entry.setContents(bytes);
            entry.setTicketId(rs.getInt("ticket_id"));
            return entry;
        }
    }

    @Override
    @Transactional
    public Attachment getAttachment(long ticketId, String name) {
        final String SQL_SELECT_ATTACHMENT = "select * from attachment"
                + " where ticket_id=? and filename=?";
        return jdbcOp.queryForObject(SQL_SELECT_ATTACHMENT,
                new AttachmentRowMapper(), ticketId, name);
    }

}
