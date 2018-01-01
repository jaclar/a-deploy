def call(String name, String region, String group) {
    sh "npm install a-roller"
    sh "/usr/bin/packer build -var 'name=${name}' -var 'region=${region}' -var 'build_number=${env.BUILD_NUMBER}' node_modules/a-roller/packer/ami.json"
    sh "node_modules/.bin/roller --region ${region} --group ${group} --ami '${name} ${env.BUILD_NUMBER}'"
}
