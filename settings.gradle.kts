rootProject.name = "services"

include("user")
include("user:api")
include("user:domain")
include("user:routing")
include("webserver")
include("education")
include("education:domain")
include("education:api")
findProject(":education:api")?.name = "api"
include("education:routing")
findProject(":education:routing")?.name = "routing"
