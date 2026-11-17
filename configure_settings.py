import urllib.request, http.cookiejar, urllib.parse, base64, json

cj = http.cookiejar.CookieJar()
opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(cj))
auth = base64.b64encode(b'admin:admin123').decode('ascii')

crumb_req = urllib.request.Request('http://localhost:8080/crumbIssuer/api/json', headers={'Authorization': 'Basic ' + auth})
crumb_data = json.loads(opener.open(crumb_req).read().decode('utf-8'))
c_field = crumb_data['crumbRequestField']
c_val = crumb_data['crumb']

groovy_script = """
import jenkins.model.*
import org.jenkinsci.plugins.configfiles.GlobalConfigFiles
import org.jenkinsci.plugins.configfiles.maven.MavenSettingsConfig

def xml = '''<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>nexus</id>
            <username>admin</username>
            <password>nexus123</password>
            <configuration>
                <httpConfiguration>
                    <all>
                        <usePreemptive>true</usePreemptive>
                    </all>
                </httpConfiguration>
            </configuration>
        </server>
        <server>
            <id>nexus-snapshots</id>
            <username>admin</username>
            <password>nexus123</password>
        </server>
    </servers>
</settings>'''

try {
    def store = GlobalConfigFiles.get()
    def config = new MavenSettingsConfig('maven-settings-nexus', 'maven-settings-nexus', 'Settings for Nexus Lab 7', xml, true, null)
    store.save(config)
    println 'SUCCESS: Maven settings config updated with preemptive auth!'
} catch (Exception e) {
    println 'ERROR: ' + e
}
"""

data = urllib.parse.urlencode({'script': groovy_script}).encode('utf-8')
script_req = urllib.request.Request(
    'http://localhost:8080/scriptText',
    data=data,
    headers={'Authorization': 'Basic ' + auth, c_field: c_val}
)
resp = opener.open(script_req)
print(resp.read().decode('utf-8'))
