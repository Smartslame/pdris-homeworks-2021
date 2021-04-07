ansible-playbook -i hosts run_db.yml --key-file "~/.ssh/id_rsa" --tags "up";
sleep 60;
ansible-playbook -i hosts run_services.yml --key-file "~/.ssh/id_rsa" --tags "up";