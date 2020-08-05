INSERT INTO `sdlc_system` (base_url, description, created_date, last_modified_date)
VALUES ('http://jira.company.com', 'Company JIRA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `sdlc_system` (base_url, description, created_date, last_modified_date)
VALUES ('http://bugzilla.company.com', 'Company BugZilla', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `sdlc_system` (base_url, description, created_date, last_modified_date)
VALUES ('http://mantis.company.com', 'Company Mantis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `project` (external_id, name, sdlc_system_id, created_date, last_modified_date)
VALUES ('SAMPLEPROJECT', 'Sample Project', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `project` (external_id, name, sdlc_system_id, created_date, last_modified_date)
VALUES ('PROJECTX', 'Project X', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `project` (external_id, name, sdlc_system_id, created_date, last_modified_date)
VALUES ('SAMPLEPROJECT', 'Sample Project', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `project` (external_id, name, sdlc_system_id, created_date, last_modified_date)
VALUES ('PROJECTX', 'Project X', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `project` (external_id, name, sdlc_system_id, created_date, last_modified_date)
VALUES ('PROJECTZERO', 'Project Zero', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `project` (external_id, name, sdlc_system_id, created_date, last_modified_date)
VALUES ('PROJECTONE', 'Project One', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `project` (external_id, name, sdlc_system_id, created_date, last_modified_date)
VALUES ('PROJECTTWO', 'Project Two', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `project` (external_id, name, sdlc_system_id, created_date, last_modified_date)
VALUES ('PROJECTTHREE', 'Project Three', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
