# Problem 11: Configuring the confirmation email in Heroku

Follow this instructions which are more detailed than the config you will get from config/application-dev.yml & config/application-prod.yml :

https://www.jhipster.tech/tips/011_tip_configuring_email_in_jhipster.html

     profiles:
        active: dev
    mail:
        host: smtp.gmail.com
        port: 587
        username: gmailuserid@gmail.com  #Replace this field with your Gmail username.
        password: ************           #Replace this field with your Gmail password.
        protocol: smtp
        tls: true
        properties.mail.smtp:
            auth: true
            starttls.enable: true
            ssl.trust: smtp.gmail.com

When using gmail you have to enable a setting "to allow access for less secure apps" for that account:

https://support.google.com/accounts/answer/6010255?hl=en

... and on your first email from a gmail address it may send an error to your email saying you logged in from a weird place (your server instead of where you created the email) and you have to confirm that.