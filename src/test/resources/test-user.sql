delete from note_user where email = 'alex@iconos.mx';
insert into note_user(id, created_on, email, last_updated_on, password) values(random_uuid(),current_timestamp,'alex@iconos.mx',current_timestamp,'password');