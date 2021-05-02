package ouhk.comps380f.dao;

import java.util.List;
import ouhk.comps380f.model.TicketUser;

public interface TicketUserRepository {

    public void save(TicketUser user);

    public List<TicketUser> findAll();

    public List<TicketUser> findById(String username);

    public void delete(String username);
}
