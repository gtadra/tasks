package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;
    private static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 10 * * *")
   // @Scheduled(fixedDelay = 10000)
    public void sendInformationEmail(){
        long size = taskRepository.count();

        Mail mail = new Mail(
                adminConfig.getAdminMail(),
                null,
                SUBJECT,
                "Currently in database you got: " + size + (size==1 ? " task" : " tasks")
        );

        MimeMessagePreparator eMail = simpleEmailService.createInformationEmail(mail);

        simpleEmailService.send(eMail);


    }
}
