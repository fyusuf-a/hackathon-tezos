export interface Perfume {
  id: number;
  name: string;
  imageUrl: string;
}

export const perfumes: Array<Perfume> = [
  {
    id: 0,
    name: "Galactic Fabric",
    imageUrl: "https://i.etsystatic.com/17099258/r/il/ef972e/1593562427/il_570xN.1593562427_687i.jpg",
  },
  {
    id: 1,
    name: "Floral Dream",
    imageUrl:
      "https://www.aufildescouleurs.com/178161-large_default/floral-dream.jpg",
  },
  {
    id: 2,
    name: "Ocean Breeze",
    imageUrl:
      "https://media.wired.com/photos/5eb9dd2b906d2cc75b592871/master/pass/Science_seabreeze_502004639.jpg",
  },
  {
    id: 3,
    name: "Citrus Splash",
    imageUrl: "https://mojo.generalmills.com/api/public/content/GYeK-lvX0ESHvrxiW2eMHA_gmi_hi_res_jpeg.jpeg?v=32673400&t=466b54bb264e48b199fc8e83ef1136b4",
  },
  {
    id: 4,
    name: "Amber Glow",
    imageUrl: "https://www.shutterstock.com/image-photo/heap-amber-stones-backlightet-glowing-600nw-1414988273.jpg",
  },
  {
    id: 5,
    name: "Vanilla Bliss",
    imageUrl: "https://lavishsoapbox.com/cdn/shop/files/PhotoRoom-20231116_094454.png?v=1700719316",
  },
  {
    id: 6,
    name: "Summer Sunset",
    imageUrl: "https://example.com/summer-sunset.jpg",
  },
  {
    id: 7,
    name: "Gardenia Whisper",
    imageUrl: "https://example.com/gardenia-whisper.jpg",
  },
  {
    id: 8,
    name: "Lavender Serenity",
    imageUrl: "https://example.com/lavender-serenity.jpg",
  },
  {
    id: 9,
    name: "Mystic Musk",
    imageUrl: "https://example.com/mystic-musk.jpg",
  },
  {
    id: 10,
    name: "Cherry Blossom",
    imageUrl: "https://example.com/cherry-blossom.jpg",
  },
  {
    id: 11,
    name: "Fresh Rain",
    imageUrl: "https://example.com/fresh-rain.jpg",
  },
  {
    id: 12,
    name: "Woodland Whisper",
    imageUrl: "https://example.com/woodland-whisper.jpg",
  },
  {
    id: 13,
    name: "Midnight Jasmine",
    imageUrl: "https://example.com/midnight-jasmine.jpg",
  },
  {
    id: 14,
    name: "Golden Amber",
    imageUrl: "https://example.com/golden-amber.jpg",
  },
  {
    id: 15,
    name: "Wild Orchid",
    imageUrl: "https://example.com/wild-orchid.jpg",
  },
  {
    id: 16,
    name: "Sunny Citrus",
    imageUrl: "https://example.com/sunny-citrus.jpg",
  },
  {
    id: 17,
    name: "Spicy Cinnamon",
    imageUrl: "https://example.com/spicy-cinnamon.jpg",
  },
  {
    id: 18,
    name: "Frosty Mint",
    imageUrl: "https://example.com/frosty-mint.jpg",
  },
  {
    id: 19,
    name: "Cedarwood Aura",
    imageUrl: "https://example.com/cedarwood-aura.jpg",
  },
  {
    id: 20,
    name: "Rose Petal",
    imageUrl: "https://example.com/rose-petal.jpg",
  },
  {
    id: 21,
    name: "Gentle Breeze",
    imageUrl: "https://example.com/gentle-breeze.jpg",
  },
  {
    id: 22,
    name: "Patchouli Passion",
    imageUrl: "https://example.com/patchouli-passion.jpg",
  },
  {
    id: 23,
    name: "Sandalwood Spice",
    imageUrl: "https://example.com/sandalwood-spice.jpg",
  },
  {
    id: 24,
    name: "Fruity Fusion",
    imageUrl: "https://example.com/fruity-fusion.jpg",
  },
  {
    id: 25,
    name: "Tropical Escape",
    imageUrl: "https://example.com/tropical-escape.jpg",
  },
  {
    id: 26,
    name: "Coconut Crush",
    imageUrl: "https://example.com/coconut-crush.jpg",
  },
  {
    id: 27,
    name: "Pear Perfection",
    imageUrl: "https://example.com/pear-perfection.jpg",
  },
  {
    id: 28,
    name: "Lemon Zest",
    imageUrl: "https://example.com/lemon-zest.jpg",
  },
  {
    id: 29,
    name: "Caramel Delight",
    imageUrl: "https://example.com/caramel-delight.jpg",
  },
  {
    id: 30,
    name: "Earthy Essence",
    imageUrl: "https://example.com/earthy-essence.jpg",
  },
];

export function getPerfume(id: number) {
  return perfumes.find((perfume) => perfume.id === id) || {
    id,
    name: `Perfume #${id}`,
    imageUrl: "https://example.com/earthy-essence.jpg",
  };
}
