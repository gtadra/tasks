package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://gtadra.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_config", adminConfig);
        context.setVariable("preview_message", "Trello new task added");
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildInformationEmail(String message){

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your db by mySQL Manager");
        functionality.add("You can add or delete data via API");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://gtadra.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_config", adminConfig);
        context.setVariable("preview_message", "current date amount in database");
        context.setVariable("application_functionality", functionality);
        context.setVariable("company_name", companyConfig.getCompanyName());

        return templateEngine.process("mail/information-db-mail", context);

    }

}
