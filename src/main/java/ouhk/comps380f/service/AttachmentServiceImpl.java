package ouhk.comps380f.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.AttachmentRepository;
import ouhk.comps380f.model.Attachment;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Resource
    private AttachmentRepository attachmentRepo;

    @Override
    @Transactional
    public Attachment getAttachment(long ticketId, String name) {
        return attachmentRepo.findByTicketIdAndName(ticketId, name);
    }
}

