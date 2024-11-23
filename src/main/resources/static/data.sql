use e_commerce_identity;
insert into users(
	joined_date,
    last_updated_date,
    email,
    first_name,
	id,
    last_name,
    phone_number,
	role_id,
    user_password,
    user_status
) values(
	now(6),
    now(6),
    "super_admin@gmail.com",
    "Cogli",
	"e6d6789a-249f-4371-9819-5ffd3409ca51",
    "ECommerce",
    "0768676878",
	"46331039-ffb5-4ac3-b12e-6fe2d7d4b39c",
    "$2a$16$3Ma59oERyZ0OeuZ6p/BO3OA278bq.EEC1UxhH3OMw7a.VsOwCcS.K",
    "ACTIVE"
);

use e_commerce_identity;
insert into roles(
	id,
	role_name,
    role_description
) values(
	"46331039-ffb5-4ac3-b12e-6fe2d7d4b39c",
	"SUPER_ADMIN",
    "This role is used to identify super admin."
),(
	"4638d7ad-ef6f-4d84-90f8-6e85a9902075",
	"ADMIN",
    "This role is used to identify admin."
),(
	"2d55e857-81a5-49a8-ba1a-241b18ed716b",
	"USER",
    "This role is used to identify user."
);