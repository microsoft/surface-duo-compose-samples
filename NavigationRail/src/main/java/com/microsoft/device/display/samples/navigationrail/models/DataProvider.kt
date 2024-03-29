/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.models

import androidx.annotation.VisibleForTesting
import com.microsoft.device.display.samples.navigationrail.R

object DataProvider {
    @VisibleForTesting
    val images: List<Image> = listOf(
        Image(
            id = 1,
            image = R.drawable.plant_1,
            name = "Asystasia gangetica",
            location = "North America, Europe",
            fact1 = "direct sunlight",
            fact2 = "2 meters",
            details = "Asystasia gangetica is usually seen in parts of North America and Europe. It enjoys cold climate, receiving direct light and is usually with smaller roots. In their best conditions they reach about a height around 1.5 meter and sometimes around 2 meters.",
        ),
        Image(
            id = 2,
            image = R.drawable.plant_2,
            name = "Ipomoea cairica",
            location = "Asia, Europe, Americas, Africa",
            fact1 = "direct sunlight",
            fact2 = "2 meters",
            details = "Ipomoea cairica is usually seen in parts of Asia, Africa, Europe, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and 2 meters."
        ),
        Image(
            id = 3,
            image = R.drawable.plant_3,
            name = "Dioscorea bulbifera",
            location = "India, North America, South America, Africa",
            fact1 = "direct sunlight",
            fact2 = "1.6 meters",
            details = "Dioscorea bulbifera is usually seen in parts of Asia, Africa, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and sometimes around 1.6 meters.",
        ),
        Image(
            id = 4,
            image = R.drawable.plant_4,
            name = "Afrocarpus falcatus",
            location = "Europe",
            fact1 = "direct sunlight",
            fact2 = "10 meters",
            details = "Afrocarpus falcatus is usually seen in parts of Europe. It enjoys damp climate in the rainforest, receiving direct light and is usually seen closer to the water streams with deeper roots extending for water. In their best conditions they reach about a height around 8 meter and sometimes around 10-12 meters.",
        ),
        Image(
            id = 5,
            image = R.drawable.plant_5,
            name = "Pilea cadierei",
            location = "India, North America, South America, Africa",
            fact1 = "direct sunlight",
            fact2 = "1.6 meters",
            details = "Pilea cadierei is usually seen in parts of Asia, Africa, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and sometimes around 1.6 meters.",
        ),
        Image(
            id = 6,
            image = R.drawable.plant_6,
            name = "Matteuccia struthiopteris",
            location = "Asia, Europe, Americas, Africa",
            fact1 = "direct sunlight",
            fact2 = "2 meters",
            details = "Matteuccia struthiopteris is usually seen in parts of Asia, Africa, Europe, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and 2 meters.",
        ),
        Image(
            id = 7,
            image = R.drawable.plant_7,
            name = "Curcuma longa",
            location = "India, North America, South America, Africa",
            fact1 = "direct sunlight",
            fact2 = "1.6 meters",
            details = "Curcuma longa is usually seen in parts of Asia, Africa, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and sometimes around 1.6 meters.",
        ),
        Image(
            id = 8,
            image = R.drawable.plant_8,
            name = "Aegopodium podagraria",
            location = "North America, Europe",
            fact1 = "direct sunlight",
            fact2 = "2 meters",
            details = "Aegopodium podagraria is usually seen in parts of North America and Europe. It enjoys cold climate, receiving direct light and is usually with smaller roots. In their best conditions they reach about a height around 1.5 meter and sometimes around 2 meters.",
        ),
        Image(
            id = 9,
            image = R.drawable.plant_9,
            name = "Homalomena rubescens",
            location = "Southern Asia",
            fact1 = "medium indirect filtered light",
            fact2 = "1.3 meters high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 10,
            image = R.drawable.plant_10,
            name = "Adiantum jordanii",
            location = "Southeast Asia",
            fact1 = "indirect filtered light",
            fact2 = "0.6 meters",
            details = "Adiantum jordanii is usually seen in southeast parts of Asia. It enjoys moist, humid environment of the rainforest floor, receiving indirect filtered light through the canopy. In their best conditions they reach about a height around 0.4 meters to 0.6 meters.",
        ),
        Image(
            id = 11,
            image = R.drawable.plant_11,
            name = "Agave sebastiana",
            location = "Africa",
            fact1 = "direct sunlight",
            fact2 = "0.3 meters",
            details = "Agave sebastiana is usually seen in parts of Africa. It enjoys dry climate, receiving direct light and is usually with deeper roots extending for water. In their best conditions they reach about a height around 0.2 meter and sometimes around 0.3 - 0.4 meters.",
        ),
        Image(
            id = 12,
            image = R.drawable.plant_12,
            name = "Chamaedorea elegans",
            location = "Europe",
            fact1 = "direct sunlight",
            fact2 = "10 meters",
            details = "Chamaedorea elegans is usually seen in parts of Europe. It enjoys damp climate in the rainforest, receiving direct light and is usually seen closer to the water streams with deeper roots extending for water. In their best conditions they reach about a height around 8 meter and sometimes around 10-12 meters.",
        ),
        Image(
            id = 13,
            image = R.drawable.bird_1,
            name = "Male common Nightingale",
            location = "Europe, Africa, Asia",
            fact1 = "wingspan 20-25 centimeters",
            fact2 = "",
            details = "The common nightingale, rufous nightingale or simply nightingale, is a small passerine bird best known for its powerful and beautiful song. It was formerly classed as a member of the thrush family Turdidae but is now more generally considered to be an Old World flycatcher, Muscicapidae.",
        ),
        Image(
            id = 14,
            image = R.drawable.bird_2,
            name = "Kingfisher",
            location = "Africa, Asia",
            fact1 = "the largest kingfisher weighs around 500 grams",
            fact2 = "",
            details = "Kingfishers or Alcedinidae are a family of small to medium-sized, brightly colored birds in the order Coraciiformes. They have a cosmopolitan distribution, with most species found in the tropical regions of Africa, Asia, and Oceania. The family contains 114 species and is divided into three subfamilies and 19 genera.",
        ),
        Image(
            id = 15,
            image = R.drawable.bird_3,
            name = "Resplendent Quetzal",
            location = "South America",
            fact1 = "33-40 centimeters long",
            fact2 = "",
            details = "The resplendent quetzal is a bird in the trogon family. It is found from Chiapas, Mexico to western Panama. It is well known for its colorful plumage. There are two subspecies, P. m. mocinno and P. m. costaricensis. The resplendent quetzal plays an important role in various types of Mesoamerican mythology.",
        ),
        Image(
            id = 16,
            image = R.drawable.bird_4,
            name = "Single Grey-headed Woodpecker",
            location = "Europe",
            fact1 = "wingspan 38-40 centimeters",
            fact2 = "",
            details = "The grey-headed woodpecker, also known as the grey-faced woodpecker, is a Eurasian member of the woodpecker family, Picidae. Along with the more commonly found European green woodpecker and the Iberian green woodpecker, it is one of three closely related sister species found in Europe.",
        ),
        Image(
            id = 17,
            image = R.drawable.bird_5,
            name = "Tawny Owl",
            location = "Europe, Asia",
            fact1 = "wingspan 100 centimeters",
            fact2 = "",
            details = "The tawny owl or brown owl is a stocky, medium-sized owl commonly found in woodlands across much of Eurasia and North Africa. Its underparts are pale with dark streaks, and the upperparts are either brown or grey. Several of the eleven recognised subspecies have both variants.",
        ),
        Image(
            id = 18,
            image = R.drawable.bird_6,
            name = "Wild Willow",
            location = "Europe, Africa",
            fact1 = "wingspan 15-20 centimeters",
            fact2 = "",
            details = "The willow is a very common and widespread leaf warbler which breeds throughout northern and temperate Europe and the Palearctic, from Ireland east to the Anadyr River basin in eastern Siberia. It is strongly migratory, with almost all the population wintering in Sub-Saharan Africa.",
        ),
        Image(
            id = 19,
            image = R.drawable.bird_7,
            name = "Toucan",
            location = "South America",
            fact1 = "grows up to 61 centimeters long",
            fact2 = "",
            details = "Toucans are members of the Neotropical near passerine bird family Ramphastidae. The Ramphastidae are most closely related to the American barbets. They are brightly marked and have large, often-colorful bills. The family includes five genera and over forty different species.",
        ),
        Image(
            id = 20,
            image = R.drawable.bird_8,
            name = "Hummingbird",
            location = "North America",
            fact1 = "wingspan 10-12 centimeters",
            fact2 = "",
            details = "Hummingbirds are birds native to the Americas and comprise the biological family Trochilidae. With about 360 species, they occur from Alaska to Tierra del Fuego, but the vast majority of the species are found in the tropics. They are small birds, with most species measuring 7.5–13 cm in length.",
        ),
        Image(
            id = 21,
            image = R.drawable.bird_9,
            name = "Pileated Woodpecker",
            location = "North America",
            fact1 = "wingspan 76 centimeters",
            fact2 = "",
            details = "The pileated woodpecker is a large, mostly black woodpecker native to North America. An insectivore, it inhabits deciduous forests in eastern North America, the Great Lakes, the boreal forests of Canada, and parts of the Pacific Coast. It is the largest extant woodpecker species in the United States.",
        ),
        Image(
            id = 22,
            image = R.drawable.bird_10,
            name = "Robin Redbreast",
            location = "Europe",
            fact1 = "wingspan 19-22.6 centimeters",
            fact2 = "",
            details = "The European robin, known simply as the robin or robin redbreast in Great Britain, is a small insectivorous passerine bird that belongs to the chat subfamily of the Old World flycatcher family.",
        ),
        Image(
            id = 23,
            image = R.drawable.bird_11,
            name = "North Island Brown Kiwi",
            location = "New Zealand",
            fact1 = "has tiny wings, but cannot fly",
            fact2 = "",
            details = "The North Island brown kiwi, is a species of kiwi that is widespread in the northern two-thirds of the North Island of New Zealand and, with about 35,000 remaining, is the most common kiwi. It holds the world record for laying the largest eggs relative to its body size.",
        ),
        Image(
            id = 24,
            image = R.drawable.bird_12,
            name = "Osprey",
            location = "Africa, Americas",
            fact1 = "wingspan 152-180 centimeters",
            fact2 = "",
            details = "The osprey or more specifically the western osprey — also called sea hawk, river hawk, and fish hawk — is a diurnal, fish-eating bird of prey with a cosmopolitan range. It is a large raptor, reaching more than 60 cm in length and 180 cm across the wings.",
        ),
        Image(
            id = 25,
            image = R.drawable.animal_1,
            name = "Red Deer",
            location = "Asia, Europe, Africa",
            fact1 = "1.2 meters tall",
            fact2 = "",
            details = "The red deer is one of the largest deer species. A male red deer is called a stag or hart, and a female is called a hind. The red deer inhabits most of Europe, the Caucasus Mountains region, Anatolia, Iran, and parts of western Asia.",
        ),
        Image(
            id = 26,
            image = R.drawable.animal_2,
            name = "Racoon",
            location = "North America",
            fact1 = "23-30 centimeters",
            fact2 = "",
            details = "The raccoon, sometimes called the common raccoon to distinguish it from other species, is a medium-sized mammal native to North America. It is the largest of the procyonid family, having a body length of 40 to 70 cm, and a body weight of 5 to 26 kg.",
        ),
        Image(
            id = 27,
            image = R.drawable.animal_3,
            name = "Bison",
            location = "Americas, Europe",
            fact1 = "2.5 meters",
            fact2 = "",
            details = "Bison are large, even-toed ungulates in the genus Bison within the subfamily Bovinae. Two extant and six extinct species are recognised. Of the six extinct species, five became extinct in the Quaternary extinction event.",
        ),
        Image(
            id = 28,
            image = R.drawable.animal_4,
            name = "Spider Monkey",
            location = "South America",
            fact1 = "53 centimeters",
            fact2 = "",
            details = "Spider monkeys are New World monkeys belonging to the genus Ateles, part of the subfamily Atelinae, family Atelidae. Like other atelines, they are found in tropical forests of Central and South America, from southern Mexico to Brazil.",
        ),
        Image(
            id = 29,
            image = R.drawable.animal_5,
            name = "Hare",
            location = "North America, Europe",
            fact1 = "40-75 centimeters",
            fact2 = "",
            details = "Hares and jackrabbits are leporids belonging to the genus Lepus. Hares are classified in the same family as rabbits. They have similar herbivorous diets, but are generally larger in size than rabbits, have proportionately longer ears and live solitarily or in pairs.",
        ),
        Image(
            id = 30,
            image = R.drawable.animal_6,
            name = "Elk",
            location = "North America, East Asia",
            fact1 = "1.5 meters tall",
            fact2 = "",
            details = "The elk, also known as the wapiti, is one of the largest species within the deer family, Cervidae, and one of the largest terrestrial mammals in North America, as well as Central and East Asia.",
        ),
        Image(
            id = 31,
            image = R.drawable.animal_7,
            name = "Hedgehog",
            location = "Europe, Asia, and Africa, New Zealand",
            fact1 = "10-30 centimeters",
            fact2 = "",
            details = "A hedgehog is a spiny mammal of the subfamily Erinaceinae, in the eulipotyphlan family Erinaceidae. There are seventeen species of hedgehog in five genera found throughout parts of Europe, Asia, and Africa, and in New Zealand by introduction.",
        ),
        Image(
            id = 32,
            image = R.drawable.animal_8,
            name = "Blue Racer",
            location = "North America",
            fact1 = "1.5 meters",
            fact2 = "",
            details = "Coluber constrictor foxii, commonly known as the blue racer, is a subspecies of Coluber constrictor, a species of nonvenomous, colubrid snakes commonly referred to as the eastern racer.",
        ),
        Image(
            id = 33,
            image = R.drawable.animal_9,
            name = "Gray Wolf",
            location = "Europe, North America",
            fact1 = "80-85 centimeters",
            fact2 = "",
            details = "The wolf, also known as the gray wolf or grey wolf, is a large canine native to Eurasia and North America. More than thirty subspecies of Canis lupus have been recognized, and gray wolves, as colloquially understood, comprise non-domestic/feral subspecies.",
        ),
        Image(
            id = 34,
            image = R.drawable.animal_10,
            name = "Asian Badger",
            location = "Asia",
            fact1 = "60-70 centimeters",
            fact2 = "",
            details = "The Asian badger, also known as the sand badger, is a species of badger native to Mongolia, China, Kazakhstan, Kyrgyzstan, the Korean Peninsula and Russia.",
        ),
        Image(
            id = 35,
            image = R.drawable.animal_11,
            name = "Brown Bear",
            location = "North America, Europe",
            fact1 = "70-152 centimeters",
            fact2 = "",
            details = "The brown bear is a large bear species found across Eurasia and North America. In North America, the populations of brown bears are called grizzly bears, while the subspecies that inhabits the Kodiak Islands of Alaska is known as the Kodiak bear.",
        ),
        Image(
            id = 36,
            image = R.drawable.animal_12,
            name = "Sika Deer",
            location = "Asia, Australia, Europe, Americas, Africa",
            fact1 = "50-109 centimeters",
            fact2 = "",
            details = "The sika deer, also known as the spotted deer or the Japanese deer, is a species of deer native to much of East Asia and introduced to other parts of the world.",
        ),
        Image(
            id = 37,
            image = R.drawable.lake_1,
            name = "Plitvice Lakes",
            location = "Europe",
            fact1 = "37-47 meters deep",
            fact2 = "",
            details = "Plitvice Lakes National Park is a 295-sq.-km forest reserve in central Croatia. It's known for a chain of 16 terraced lakes, joined by waterfalls, that extend into a limestone canyon. Walkways and hiking trails wind around and across the water, and an electric boat links the 12 upper and 4 lower lakes.",
        ),
        Image(
            id = 38,
            image = R.drawable.lake_2,
            name = "Maligne Lake",
            location = "North America",
            fact1 = "1,656 meters above sea level",
            fact2 = "",
            details = "Maligne Lake is a lake in Jasper National Park, Alberta, Canada. The lake is famed for the colour of its azure water, the surrounding peaks, the three glaciers visible from the lake, and Spirit Island, a frequently and very famously photographed islet.",
        ),
        Image(
            id = 39,
            image = R.drawable.lake_3,
            name = "Gosausee",
            location = "Europe",
            fact1 = "105 meters above sea level",
            fact2 = "",
            details = "Gosausee is the largest freshwater lake in Austria that supports natural biodiversity. Here the lake prohibits fishing activities due to its depleting fish species.",
        ),
        Image(
            id = 40,
            image = R.drawable.lake_4,
            name = "Turquoise Lake",
            location = "North America",
            fact1 = "744 meters above sea level",
            fact2 = "",
            details = "Popular cycling & driving route around an 1,800-acre glacial lake named for a bygone turquoise mine. They are usually the most touristy getaway locations for people living in Colorado.",
        ),
        Image(
            id = 41,
            image = R.drawable.lake_5,
            name = "Monticchio Lakes",
            location = "Europe",
            fact1 = "658 meters above sea level",
            fact2 = "",
            details = "Some call them the “Vulture Twins”, but the two volcanic lakes in Monticchio, in the municipalities of Atella and Rionero in Vulture -- Italy, are actually quite different. The Small Lake is located at 658 meters above sea level and feeds into the Great Lake, two meters below: as the names suggest, they differ in size – one is barely over 16 hectares, while the other expands over some 38.",
        ),
        Image(
            id = 42,
            image = R.drawable.lake_6,
            name = "Leavenworth",
            location = "North America",
            fact1 = "1,695 meters above sea level",
            fact2 = "",
            details = "Colchuck Lake is a freshwater reservoir lake located on the western slope of The Enchantments, in Chelan County, Washington. The lake is located approximately 24 kilometers from the city of Leavenworth, Washington and sits on the southeast corner of the Icicle Creek subbasin",
        ),
        Image(
            id = 43,
            image = R.drawable.lake_7,
            name = "Seven Rila Lakes",
            location = "Europe",
            fact1 = "2,100 meters above sea level",
            fact2 = "",
            details = "The Seven Rila Lakes are a group of glacial lakes, situated in the northwestern Rila Mountain in Bulgaria. They are the most visited group of lakes in Bulgaria. The lakes are situated between 2,100 and 2,500 meters elevation above sea level. Each lake carries a name associated with its most characteristic feature.",
        ),
        Image(
            id = 44,
            image = R.drawable.lake_8,
            name = "Joffre Lakes",
            location = "North America",
            fact1 = "1,661 meters above sea level",
            fact2 = "",
            details = "The vivid blue waters of Upper, Middle, and Lower Joffre lakes were created from glacial silt and this reflects the blue and green wavelengths of sunlight, creating a vibrant turquoise color. This phenomenon, along with stunning icefields, jagged peaks, and cold rushing streams, granted Joffre Lakes park status in 1996.",
        ),
        Image(
            id = 45,
            image = R.drawable.lake_9,
            name = "Masuria Lake",
            location = "Europe",
            fact1 = "244 meters above sea level",
            fact2 = "",
            details = "The Masurian Lake District or Masurian Lakeland is a lake district in northeastern Poland within the geographical region of Masuria, in the past inhabited by Masurians who spoke the Masurian dialect. It contains more than 2,000 lakes.",
        ),
        Image(
            id = 46,
            image = R.drawable.lake_10,
            name = "Plitvice Lakes",
            location = "Europe",
            fact1 = "37-47 meters deep",
            fact2 = "",
            details = "Plitvice Lakes National Park is a 295-sq.-km forest reserve in central Croatia. It's known for a chain of 16 terraced lakes, joined by waterfalls, that extend into a limestone canyon. Walkways and hiking trails wind around and across the water, and an electric boat links the 12 upper and 4 lower lakes.",
        ),
        Image(
            id = 47,
            image = R.drawable.lake_11,
            name = "Fusine Lake",
            location = "Europe",
            fact1 = "1,021 meters above sea level",
            fact2 = "",
            details = "The Fusine Lake was a large endorheic lake in western Abruzzo, central Italy, stretching from Avezzano in the northwest to Ortucchio in the southeast, and touching Trasacco in the southwest. Once the third largest lake in Italy, it was drained in 1878.",
        ),
        Image(
            id = 48,
            image = R.drawable.lake_12,
            name = "Lovatnet Lake",
            location = "Europe",
            fact1 = "52 meters above sea level",
            fact2 = "",
            details = "Lovatnet is a lake in the municipality of Stryn in Vestland county, Norway. It is located about 2 kilometers southeast of the village of Loen and about 6 kilometers east of the village of Olden. The lake lies just 2 kilometres southwest of the mountain Skåla.",
        ),
        Image(
            id = 49,
            image = R.drawable.rock_1,
            name = "Novaculite",
            location = "Asia",
            fact1 = "rich in silica",
            fact2 = "",
            details = "Novaculite, also called Arkansas Stone, is a microcrystalline to cryptocrystalline rock type that consists of silica in the form of chert or flint. It is commonly white to grey or black in color, with a specific gravity that ranges from 2.2 to 2.5. It is used in the production of sharpening stones.",
        ),
        Image(
            id = 50,
            image = R.drawable.rock_2,
            name = "Chert",
            location = "North America",
            fact1 = "rich in silica",
            fact2 = "",
            details = "Chert is a hard, fine-grained sedimentary rock composed of microcrystalline or cryptocrystalline quartz, the mineral form of silicon dioxide. Chert is characteristically of biological origin, but may also occur inorganically as a chemical precipitate or a diagenetic replacement, as in petrified wood.",
        ),
        Image(
            id = 51,
            image = R.drawable.rock_3,
            name = "Hornfels",
            location = "Europe and Americas",
            fact1 = "rich in quartz",
            fact2 = "",
            details = "Hornfels is the group name for a set of contact metamorphic rocks that have been baked and hardened by the heat of intrusive igneous masses and have been rendered massive, hard, splintery, and in some cases exceedingly tough and durable.",
        ),
        Image(
            id = 52,
            image = R.drawable.rock_4,
            name = "Obsidian",
            location = "North America",
            fact1 = "rich in silicon, oxygen, aluminium, sodium, potassium",
            fact2 = "",
            details = "Obsidian is a naturally occurring volcanic glass formed when lava extruded from a volcano cools rapidly with minimal crystal growth. It is an igneous rock. Obsidian is produced from felsic lava, rich in the lighter elements such as silicon, oxygen, aluminium, sodium, and potassium.",
        ),
        Image(
            id = 53,
            image = R.drawable.rock_5,
            name = "Mariposite",
            location = "North America",
            fact1 = "rich in chromium",
            fact2 = "",
            details = "Mariposite is a mineral which is a chromium-rich variety of mica, which imparts an attractive green color to the generally white dolomitic marble in which it is commonly found. It was named for Mariposa, California, though it can be found in several places in the Sierra Nevada mountains.",
        ),
        Image(
            id = 54,
            image = R.drawable.rock_6,
            name = "Shale",
            location = "North America",
            fact1 = "rich in iron oxides and clay ",
            fact2 = "",
            details = "Shale is a fine-grained, clastic sedimentary rock formed from mud that is a mix of flakes of clay minerals and tiny fragments of other minerals, especially quartz and calcite. Shale is characterized by its tendency to split into thin layers less than one centimeter in thickness.",
        ),
        Image(
            id = 55,
            image = R.drawable.rock_7,
            name = "Dacite",
            location = "All over Earth",
            fact1 = "rich in silica and metal oxides",
            fact2 = "",
            details = "Dacite is a volcanic rock formed by rapid solidification of lava that is high in silica and low in alkali metal oxides. It has a fine-grained to porphyritic texture and is intermediate in composition between andesite and rhyolite. It is composed predominantly of plagioclase feldspar and quartz.",
        ),
        Image(
            id = 56,
            image = R.drawable.rock_8,
            name = "Peridotite",
            location = "Europe",
            fact1 = "rich in magnesium and iron",
            fact2 = "",
            details = "Peridotite is a dense, coarse-grained igneous rock consisting mostly of the silicate minerals olivine and pyroxene. Peridotite is ultramafic, as the rock contains less than 45% silica. It is high in magnesium, reflecting the high proportions of magnesium-rich olivine, with appreciable iron.",
        ),
        Image(
            id = 57,
            image = R.drawable.rock_9,
            name = "Andesite",
            location = "South America",
            fact1 = "ferromagnesian mineral",
            fact2 = "",
            details = "Andesite is an extrusive volcanic rock of intermediate composition. In a general sense, it is the intermediate type between basalt and rhyolite. It is fine-grained to porphyritic in texture, and is composed predominantly of sodium-rich plagioclase plus pyroxene or hornblende.",
        ),
        Image(
            id = 58,
            image = R.drawable.rock_10,
            name = "Phyllite",
            location = "All over Earth",
            fact1 = "rich in quartz and chlorite",
            fact2 = "",
            details = "Phyllite is a type of foliated metamorphic rock created from slate that is further metamorphosed so that very fine grained white mica achieves a preferred orientation. It is primarily composed of quartz, sericite mica, and chlorite.",
        ),
        Image(
            id = 59,
            image = R.drawable.rock_11,
            name = "Slate",
            location = "All over Earth",
            fact1 = "slate's color depends on the amount of iron it contains",
            fact2 = "",
            details = "Slate is a fine-grained, foliated, homogeneous metamorphic rock derived from an original shale-type sedimentary rock composed of clay or volcanic ash through low-grade regional metamorphism.",
        ),
        Image(
            id = 60,
            image = R.drawable.rock_12,
            name = "Basalt",
            location = "All over Earth",
            fact1 = "rich in iron and magnesium minerals",
            fact2 = "",
            details = "Basalt is an aphanitic extrusive igneous rock formed from the rapid cooling of low-viscosity lava rich in magnesium and iron exposed at or very near the surface of a rocky planet or moon. More than 90% of all volcanic rock on Earth is basalt.",
        ),
    )

    val plantList = images.subList(0, 12)
    val birdList = images.subList(12, 24)
    val animalList = images.subList(24, 36)
    val lakeList = images.subList(36, 48)
    val rockList = images.subList(48, 60)

    fun getImage(imageId: Int) = images.find { it.id == imageId }
}
