def call(String name, String region, String group, String build_number) {
    sh "npm install a-roller"
    sh "node_modules/.bin/roller --region ${region} --group ${group} --ami '${name} ${build_number}'"
}
