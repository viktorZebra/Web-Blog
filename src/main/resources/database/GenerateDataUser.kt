import io.github.serpro69.kfaker.Faker
import java.io.File

fun main() {

    fillTable("src/main/resources/usersDump.sql", genUsers())
}

fun fillTable(dumpFilepath: String, data: List<String>) {
    File(dumpFilepath).printWriter().use { out ->
        data.forEach {
            out.println(it)
        }
    }
}

fun genUsers(): ArrayList<String> {
    val faker = Faker()
    val listUser: ArrayList<String> = arrayListOf()
    listUser.add("INSERT INTO users (fullname, email, nickname, id, about, count_view_profile) VALUES")

    for (i in 0..1000) {
        val name = faker.name
        val fullname = name.firstName().replace("'", " ") + " " + name.lastName().replace("'", " ")
        val email = faker.internet.email().replace("'", "") + i.toString()
        val nickname = faker.dcComics.name() + i.toString()
        val about = faker.elderScrolls.dragon()
        val count = (0..30).random()

        listUser.add("('$fullname','$email','$nickname',$i,'$about','$count'),")
    }

    return listUser
}