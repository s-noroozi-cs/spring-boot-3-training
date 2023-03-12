insert into account_entity (id,bic,iban)
                    values(100,'MELIIRTHXXX','000');
insert into account_entity (id,bic,iban)
                    values(101,'BSIRIRTHXXX','111');
insert into account_entity (id,bic,iban)
                    values(102,'BKMTIRTHXXX','222');

insert into person_entity (id,account_ids,address,name,phone)
                    values(200,100,'tehran','ali','0912');
insert into person_entity (id,account_ids,address,name,phone)
                    values(201,101,'karaj','reza','0935');
insert into person_entity (id,account_ids,address,name,phone)
                    values(202,102,'tabriz','ahmad','0901');

insert into payment_entity (id,amount,date_time,payee_person_id,payer_person_id)
                    values(301,111,CURRENT_TIMESTAMP,200,201);
insert into payment_entity (id,amount,date_time,payee_person_id,payer_person_id)
                    values(302,222,CURRENT_TIMESTAMP,201,202);
insert into payment_entity (id,amount,date_time,payee_person_id,payer_person_id)
                    values(303,333,CURRENT_TIMESTAMP,202,200);


