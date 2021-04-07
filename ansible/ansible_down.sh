ansible-playbook -i hosts run_db.yml --key-file "~/.ssh/id_rsa" --tags "down";
ansible-playbook -i hosts run_services.yml --key-file "~/.ssh/id_rsa" --tags "down";