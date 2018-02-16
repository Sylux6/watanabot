package modules.llsif.entity;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public enum Idol {
    HONOKA  	(
    	        "Kousaka Honoka", 
    	        "高坂 穂乃果", 
    	        16, 
    	        "Otonokizaka Academy", 
    	        "08-03", 
    	        "Leo", 
    	        "O", 
    	        157, 
    	        "B78 / W58 / H82", 
    	        "Strawberries", 
    	        "Bell peppers", 
    	        "Swimming and Collecting Stickers", 
    	        "Second", 
    	        "μ's", 
    	        "Printemps", 
    	        "Nitta Emi", 
    	        "Emitsun", 
    	        "A 16-year-old, second-year high school student and founder of µ's. She is always smiling, and her redeeming feature is her energy. She acts on impulse and always runs head-first into things once shes made up her mind. Any and all problems are overcome with her inherent optimism, making her the engine and driving force behind µ's.",
	    	"https://vignette.wikia.nocookie.net/love-live/images/c/c6/Love_Live%21_infobox_-_Kousaka_Honoka.png/revision/latest?cb=20170402074116"
	    	),   
    ELI     	(
		"Ayase Eli", 
		"絢瀬 絵里", 
		17, 
		"Otonokizaka Academy", 
		"10-21", 
		"Libra", 
		"B", 
		162, 
		"B88 / W60 / H84", 
		"Chocolate", 
		"Dried plums, seaweed", 
		"Making Quilts and Accessories", 
		"Third", 
		"μ's", 
		"Bibi", 
		"Nanjou Yoshino", 
		"Nanjolno", 
		"A 17-year-old, third-year high school student and the student council president. She is one-quarter Russian. With a sharp mind and superb athletic abilities, she does everything thrown at her flawlessly. Popular throughout the school and having a strong sense of responsibility, she performs her duties as the student council president well.",
		"https://vignette.wikia.nocookie.net/love-live/images/2/2b/Love_Live%21_infobox_-_Ayase_Eli.png/revision/latest?cb=20170402074117"
	    	),
    KOTORI  	(
	        "Minami Kotori", 
	        "南 ことり", 
	        16, 
	        "Otonokizaka Academy", 
	        "09-12", 
	        "Virgo", 
	        "O", 
	        159, 
	        "B80 / W58 / H80", 
	        "Cheesecake", 
	        "Garlic", 
	        "Making Sweets", 
	        "Second", 
	        "μ's", 
	        "Printemps", 
	        "Uchida Aya", 
	        "Ucchi", 
	        "A 16-year-old, second-year high school student and  Honoka's closest friend. They've been together since kindergarten. In contrast to Honoka, she has a kind and gentle personality, focuses on her studies, and is a model student. Although she's so gentle, she's also confident and reliable.",
	        "https://vignette.wikia.nocookie.net/love-live/images/b/bc/Love_Live%21_infobox_-_Minami_Kotori.png/revision/latest?cb=20170402074118"
	    	),
    UMI		(
	        "Sonoda Umi", 
	        "園田 海未", 
	        16, 
	        "Otonokizaka Academy", 
	        "03-15", 
	        "Pisces", 
	        "A", 
	        159, 
	        "B76 / W58 / H80", 
	        "Honokas familys manjuu", 
	        "Carbonated drinks", 
	        "Reading and Calligraphy", 
	        "Second", 
	        "μ's", 
	        "Lily White", 
	        "Mimori Suzuko", 
	        "Mimorin", 
	        "A 16-year-old, second-year high school student and the childhood friend of Honoka and Kotori. Raised in a family that runs a school of traditional Japanese dance, she is a refined girl with perfect manners and has a dignified air around her. She has also practiced archery ever since she was little. She is strict with herself and with others and hates misconduct and laziness.",
	        "https://vignette.wikia.nocookie.net/love-live/images/3/36/Love_Live%21_infobox_-_Sonoda_Umi.png/revision/latest?cb=20170402074119"
	    	),
    RIN		(
	        "Hoshizora Rin", 
	        "星空 凛", 
	        15, 
	        "Otonokizaka Academy", 
	        "11-01", 
	        "Scorpio", 
	        "A", 
	        155, 
	        "B75 / W59 / H80", 
	        "Ramen", 
	        "Fish", 
	        "All Sports", 
	        "First", 
	        "μ's", 
	        "Lily White", 
	        "Iida Riho", 
	        "Rippi", 
	        "A 15-year-old, first-year high school student and an energetic girl who's fond of sports. She would rather move her body than worry about things. She ends up participating in everything just because they sound interesting. Most likely due to her involvement in sports, she is very helpful and often looks after her childhood friend, Hanayo. Her responses are always full of energy and she puts her all into practices.",
	        "https://vignette.wikia.nocookie.net/love-live/images/3/38/Love_Live%21_infobox_-_Hoshizora_Rin.png/revision/latest?cb=20170402074119"
	    	),
    MAKI	(
	        "Nishikino Maki", 
	        "西木野 真姫", 
	        15, 
	        "Otonokizaka Academy", 
	        "04-19", 
	        "Aries", 
	        "AB", 
	        161, 
	        "B78 / W56 / H83", 
	        "Tomatoes", 
	        "Tangerines", 
	        "Stargazing", 
	        "First", 
	        "μ's", 
	        "Bibi", 
	        "Pile", 
	        "Pile", 
	        "A 15-year-old, first-year high school student and the daughter of a wealthy family—her parents run a large hospital. Her singing is top-notch and she can also play the piano. Haughty and proud, she doesn't reveal her true emotions often. Her courageous nature allows her to argue with older students, but there is also a side to her which desires company.",
	        "https://vignette.wikia.nocookie.net/love-live/images/2/24/Love_Live%21_infobox_-_Nishikino_Maki.png/revision/latest?cb=20170402074121"
	    	),
    NOZOMI	(
	        "Toujou Nozomi", 
	        "東條 希", 
	        17, 
	        "Otonokizaka Academy", 
	        "06-09", 
	        "Gemini", 
	        "O", 
	        159, 
	        "B90 / W60 / H82", 
	        "Yakiniku", 
	        "Caramel", 
	        "Taking Naps and Fortune-Telling", 
	        "Third", 
	        "μ's", 
	        "Lily White", 
	        "Kusuda Aina", 
	        "Kussun", 
	        "A 17-year-old, third-year high school student and the student council vice-president. Her relatively carefree personality is the complete opposite of Eli's, and she speaks with a peculiar mix of a Kansai accent and regular Japanese. She makes a good team with the cool Eli. She has a big heart and is the oldest of all the members. While she appears to be indifferent to most things, she is also quite the schemer.",
    	        "https://vignette.wikia.nocookie.net/love-live/images/1/14/Love_Live%21_infobox_-_Toujou_Nozomi.png/revision/latest?cb=20170402074122"
	    	),
    HANAYO	(
	        "Koizumi Hanayo", 
	        "小泉 花陽", 
	        15, 
	        "Otonokizaka Academy", 
	        "01-17", 
	        "Capricorn", 
	        "B", 
	        156, 
	        "B82 / W60 / H83", 
	        "White rice", 
	        "None", 
	        "Drawing", 
	        "First", 
	        "μ's", 
	        "Printemps", 
	        "Kubo Yurika", 
	        "Shikaco", 
	        "A 15-year-old, first-year high school student. She's a quiet girl who doesn't stand out much in class and loves white rice. She lacks self-esteem and is quick to give up on almost anything she does. Admiring µ's, she joins the group along with Rin, her closest friend and the person she's always together with, and Maki.",
	        "https://vignette.wikia.nocookie.net/love-live/images/7/70/Love_Live%21_infobox_-_Koizumi_Hanayo.png/revision/latest?cb=20170402074123"
	    	),
    NICO	(
	        "Yazawa Nico", 
	        "矢澤 にこ", 
	        17, 
	        "Otonokizaka Academy", 
	        "07-22", 
	        "Cancer", 
	        "A", 
	        154, 
	        "B74 / W57 / H79", 
	        "Sweets", 
	        "Spicy food", 
	        "Fashion", 
	        "First", 
	        "μ's", 
	        "Bibi", 
	        "Tokui Sora", 
	        "Soramaru", 
	        "A 17-year-old, third-year high school student and a true idol otaku. As an upperclassman who's trying her hardest around the clock to become an idol, she frequently comes into contact with Honoka and the others while acting like a big shot. However, it turns out that she often makes mistakes and is unexpectedly clumsy. Her favorite saying is Nico Nico Nii.",
	        "https://vignette.wikia.nocookie.net/love-live/images/3/39/Love_Live%21_infobox_-_Yazawa_Nico.png/revision/latest?cb=20170402074124"
	    	),
    
    CHIKA	(
	        "Takami Chika", 
	        "高海 千歌", 
	        16, 
	        "Uranohoshi Girls' High School", 
	        "08-01", 
	        "Leo", 
	        "B", 
	        157, 
	        "B82 / W59 / H83", 
	        "Oranges", 
	        "Coffee, Shiokara", 
	        "Softball, Karaoke", 
	        "Second", 
	        "Aqours", 
	        "CYaRon!", 
	        "Inami Anju", 
	        "Anchan", 
	        "The second-year student at Uranohoshi Girl's High School who launched Aqours. The youngest of three sisters, she comes from a family that runs a traditional Japanese inn and is proud of their open-air, ocean-view bath. She's sociable and hates giving up. Her positive, pushy attitude gradually affects everyone around her.",
	        "https://vignette.wikia.nocookie.net/love-live/images/9/94/Sunshine%21%21_infobox_-_Takami_Chika.png/revision/latest?cb=20170402074002"
	    	),
    RIKO	(
	        "Sakurauchi Riko", 
	        "桜内 梨子", 
	        16, 
	        "Uranohoshi Girls' High School", 
	        "09-19", 
	        "Virgo", 
	        "A", 
	        160, 
	        "B80 / W58 / H80", 
	        "Hard-boiled Eggs, Sandwiches", 
	        "Bell Peppers", 
	        "Drawing, Handicraft, Cooking", 
	        "Second", 
	        "Aqours", 
	        "Guilty Kiss", 
	        "Rikako Aida", 
	        "Rikyako", 
	        "A second-year from Akihabara who transferred into Chika's class. An average girl with a reserved personality who would rather stay indoors than go out. While she seems mature and collected, she is actually quite careless, frequently jumping to conclusions and making mistakes.",
	        "https://vignette.wikia.nocookie.net/love-live/images/7/77/Sunshine%21%21_infobox_-_Sakurauchi_Riko.png/revision/latest?cb=20170402074004"
	    	),
    KANAN	(
	        "Matsuura Kanan", 
	        "松浦 果南", 
	        17, 
	        "Uranohoshi Girls' High School", 
	        "02-10", 
	        "Aquarius", 
	        "O", 
	        162, 
	        "B83 / W58 / H84", 
	        "Sea Snail, Seaweed", 
	        "Pickled Plums", 
	        "Astronomy, Swimming", 
	        "Third", 
	        "Aqours", 
	        "AZALEA", 
	        "Suwa Nanaka", 
	        "Suwawa", 
	        "A third-year student at Uranohoshi Girl's High School. She lives with her grandfather, who runs a diving shop on a nearby island. A mature but laid-back person, she rarely sweats the details and usually maintains a calm, cool demeanor.",
	        "https://vignette.wikia.nocookie.net/love-live/images/5/56/Sunshine%21%21_infobox_-_Matsuura_Kanan.png/revision/latest?cb=20170402074005"
	    	),
    DIA		(
	        "Kurosawa Dia", 
	        "黒澤 ダイヤ", 
	        17, 
	        "Uranohoshi Girls' High School", 
	        "01-01", 
	        "Capricorn", 
	        "A", 
	        162, 
	        "B80 / W57 / H80", 
	        "Green Tea-Flavored Sweets, Flan", 
	        "Salisbury Steak, Gratin", 
	        "Watching Movies, Reading", 
	        "Third", 
	        "Aqours", 
	        "AZALEA", 
	        "Komiya Arisa", 
	        "Arisha", 
	        "A third-year student and president of the student council. Hails from an old fishing family whose name is well-known around the area. Prideful and a perfectionist, she can't sit still when things are done sloppily or incorrectly.",
	        "https://vignette.wikia.nocookie.net/love-live/images/6/6c/Sunshine%21%21_infobox_-_Kurosawa_Dia.png/revision/latest?cb=20170402074006"
	    	),
    YOU		(
	        "Watanabe You", 
	        "渡辺 曜", 
	        16, 
	        "Uranohoshi Girls' High School", 
	        "04-17", 
	        "Aries", 
	        "AB", 
	        157, 
	        "B82 / W57 / H81", 
	        "Salisbury Steak, Oranges", 
	        "Sashimi, Anything too dry", 
	        "Weight Training", 
	        "Second", 
	        "Aqours", 
	        "CYaRon!", 
	        "Saitou Shuka", 
	        "Shukashuu", 
	        "A second-year student and Chika's classmate. Good enough at the high dive to qualify for the national team. A sporty type whose hobby is weight training, she tends to take action without thinking matters over first. Her father captains a ferry, and her dream is to have his job someday.",
	        "https://vignette.wikia.nocookie.net/love-live/images/0/0e/Sunshine%21%21_infobox_-_Watanabe_You.png/revision/latest?cb=20170402074007"
	    	),
    YOSHIKO	(
	        "Tsushima Yoshiko", 
	        "津島 善子", 
	        15, 
	        "Uranohoshi Girls' High School", 
	        "07-13", 
	        "Cancer", 
	        "O", 
	        156, 
	        "B79 / W58 / H80", 
	        "Chocolate, Strawberries", 
	        "Oranges", 
	        "Little devil-style fashion", 
	        "First", 
	        "Aqours", 
	        "Guilty Kiss", 
	        "Kobayashi Aika", 
	        "Aikyan", 
	        "A first-year student who likes the little devil look and proclaims to be Fallen Angel Jeanne. Born in the urban side of Numazu city, she's bright, fearless, smart, and thoughtful. However, she has extremely bad luck, running into all kinds of unforeseen trouble wherever she goes.",
	        "https://vignette.wikia.nocookie.net/love-live/images/3/39/Sunshine%21%21_infobox_-_Tsushima_Yoshiko.png/revision/latest?cb=20170402074008"
	    	),
    HANAMARU	(
	        "Kunikida Hanamaru", 
	        "国木田 花丸", 
	        15, 
	        "Uranohoshi Girls' High School", 
	        "03-04", 
	        "Pisces", 
	        "O", 
	        152, 
	        "B83 / W57 / H83", 
	        "Oranges, Sweet Red Bean Paste", 
	        "Milk, Noodles", 
	        "Reading", 
	        "First", 
	        "Aqours", 
	        "AZALEA", 
	        "Takatsuki Kanako", 
	        "King", 
	        "A first-year student and the daughter of a family that has run a nearby temple for several generations. An avid reader, she has a deep fondness for Japanese literature. She's also a gifted singer, earning her a spot in a choir. She is gentle and cares for those around her, but is prone to running around in circles.",
	        "https://vignette.wikia.nocookie.net/love-live/images/c/c1/Sunshine%21%21_infobox_-_Kunikida_Hanamaru.png/revision/latest?cb=20170402074009"
	    	),
    MARI	(
	        "Ohara Mari", 
	        "小原 鞠莉", 
	        17, 
	        "Uranohoshi Girls' High School", 
	        "06-13", 
	        "Gemini", 
	        "AB", 
	        163, 
	        "B87 / W60 / H84", 
	        "Coffee, Lemons", 
	        "Natto, Kimchi", 
	        "Sports, Horseback Riding", 
	        "Third", 
	        "Aqours", 
	        "Guilty Kiss", 
	        "Suzuki Ainao", 
	        "Ai-nya", 
	        "A third-year student. Her mother is Japanese, while her father is Italian-American and manages a hotel chain. She has a cheerful personality, she often acts independently. She always keeps her chin up when faced with hardship and ready to try her hand at anything.",
	        "https://vignette.wikia.nocookie.net/love-live/images/1/14/Sunshine%21%21_infobox_-_Ohara_Mari.png/revision/latest?cb=20170402073947"
	    	),
    RUBY	(
	        "Kurosawa Ruby", 
	        "黒澤 ルビィ", 
	        15, 
	        "Uranohoshi Girls' High School", 
	        "09-21", 
	        "Virgo", 
	        "A", 
	        154, 
	        "B76 / W56 / H79", 
	        "French Fries, Sweet Potatoes", 
	        "Wasabi", 
	        "Clothing, Needlework", 
	        "First", 
	        "Aqours", 
	        "CYaRon!", 
	        "Furihata Ai", 
	        "Aiai", 
	        "A first-year student who's almost always with her good friend Hanamaru. She is cowardly and tends to cry a lot, but she still has a tough interior, forged by her role as the daughter of a rich, well-known family. She has always looked up to idols and needlework is the only thing she's really great at.",
	        "https://vignette.wikia.nocookie.net/love-live/images/2/21/Sunshine%21%21_infobox_-_Kurosawa_Ruby.png/revision/latest?cb=20170402073946"
	    	);
    
    private String name;
    private String jp_name;
    private int age;
    private String school;
    private String birthday;
    private String astro;
    private String blood;
    private int heigth;
    private String measurements;
    private String favorite_food;
    private String least_favorite_food;
    private String hobbies;
    private String year;
    private String unit;
    private String subunit;
    private String cv_name;
    private String cv_nickname;
    private String summary;
    private String img;
    
    private Idol(String name, String jp_name, int age, String school, String birthday, String astro, String blood,
	    int heigth, String measurements, String favorite_food, String least_favorite_food, String hobbies,
	    String year, String unit, String subunit, String cv_name, String cv_nickname, String summary, String img) {
	this.name = name;
	this.jp_name = jp_name;
	this.age = age;
	this.school = school;
	this.birthday = birthday;
	this.astro = astro;
	this.blood = blood;
	this.heigth = heigth;
	this.measurements = measurements;
	this.favorite_food = favorite_food;
	this.least_favorite_food = least_favorite_food;
	this.hobbies = hobbies;
	this.year = year;
	this.unit = unit;
	this.subunit = subunit;
	this.cv_name = cv_name;
	this.cv_nickname = cv_nickname;
	this.summary = summary;
	this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getJp_name() {
        return jp_name;
    }

    public int getAge() {
        return age;
    }

    public String getSchool() {
        return school;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAstro() {
        return astro;
    }

    public String getBlood() {
        return blood;
    }

    public int getHeigth() {
        return heigth;
    }

    public String getMeasurements() {
        return measurements;
    }

    public String getFavorite_food() {
        return favorite_food;
    }

    public String getLeast_favorite_food() {
        return least_favorite_food;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getYear() {
        return year;
    }

    public String getUnit() {
        return unit;
    }

    public String getSubunit() {
        return subunit;
    }

    public String getCv_name() {
        return cv_name;
    }

    public String getCv_nickname() {
        return cv_nickname;
    }

    public String getSummary() {
        return summary;
    }
    
    public String getImg() {
	return img;
    }
    
    public MessageEmbed toEmbed() {
	return new EmbedBuilder()
		.setThumbnail(img)
		.setTitle(name+" ("+jp_name+")")
		.setDescription("CV. "+cv_name+" ("+cv_nickname+")")
		.addField("Birthday", birthday+" ("+astro+")", true)
		.addField("Blood Type", blood, true)
		.addField("Height", String.valueOf(heigth), true)
		.addField("Three Sizes", measurements, true)
		.addField("Favorite Food", favorite_food, true)
		.addField("Disliked Food", least_favorite_food, true)
		.addField("Hobbies", hobbies, false)
		.addField("Background", summary, false)
		.build();
    }
    
    public String toString() {
	return name+" "+cv_name+" "+cv_nickname;
    }
    
}
