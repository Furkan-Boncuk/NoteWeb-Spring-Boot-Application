create table note(
                     notes_id serial primary key,
                     notes_title varchar(50) not null,
                     notes_category varchar(50) not null,
                     notes_created_date timestamp default current_timestamp,
                     notes_updated_date timestamp default current_timestamp,
                     note_content text
);