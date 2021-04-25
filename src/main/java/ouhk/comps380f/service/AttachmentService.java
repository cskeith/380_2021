package ouhk.comps380f.service;

import ouhk.comps380f.model.Attachment;

public interface AttachmentService {

    public Attachment getAttachment(long ticketId, String name);
}

