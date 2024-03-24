export interface Perfume {
  id: number;
  name: string;
  imageUrl: string;
  creatorName: string;
  description: string;
  avatarUrl?: string;
}

import elena from "~/assets/Elena Miro.png";
import liam from "~/assets/Liam Neeson.png";
import marco from "~/assets/Marco Vittorio.png";
import sofia from "~/assets/Sofia Lorenz.png";

export const perfumes: Array<Perfume> = [
  {
    "id": 0,
    "name": "Galactic Fabric",
    "imageUrl": "https://i.etsystatic.com/17099258/r/il/ef972e/1593562427/il_570xN.1593562427_687i.jpg",
    "creatorName": "Elena Miro",
    "description": "A cosmic journey encapsulated in a scent, combining the mystique of the cosmos with a touch of starlight peppermint.",
    "avatarUrl": elena,
  },
  {
    "id": 1,
    "name": "Floral Dream",
    "imageUrl": "https://www.aufildescouleurs.com/178161-large_default/floral-dream.jpg",
    "creatorName": "Marco Vittorio",
    "description": "An enchanting bouquet of rare blooms, offering a dreamlike escape to a world filled with the aroma of spring.",
    "avatarUrl": marco,
  },
  {
    "id": 2,
    "name": "Ocean Breeze",
    "imageUrl": "https://media.wired.com/photos/5eb9dd2b906d2cc75b592871/master/pass/Science_seabreeze_502004639.jpg",
    "creatorName": "Sofia Lorenz",
    "description": "Capture the essence of the sea with a fresh blend of saltwater and a hint of sandy shores, invigorating the spirit with each wave.",
    "avatarUrl": sofia,
  },
  {
    "id": 3,
    "name": "Citrus Splash",
    "imageUrl": "https://mojo.generalmills.com/api/public/content/GYeK-lvX0ESHvrxiW2eMHA_gmi_hi_res_jpeg.jpeg?v=32673400&t=466b54bb264e48b199fc8e83ef1136b4",
    "creatorName": "Liam Neeson",
    "description": "A vibrant burst of lemon, lime, and orange, this scent energizes and refreshes, mirroring the first sip of a cool citrus drink on a hot day.",
    "avatarUrl": liam,
  },
  {
    "id": 4,
    "name": "Amber Glow",
    "imageUrl": "https://www.shutterstock.com/image-photo/heap-amber-stones-backlightet-glowing-600nw-1414988273.jpg",
    "creatorName": "Elena Miro",
    "description": "Warm and inviting, this fragrance combines the richness of amber with a touch of vanilla, evoking the comforting glow of a sunset.",
    "avatarUrl": elena,
  },
  {
    "id": 5,
    "name": "Vanilla Bliss",
    "imageUrl": "https://lavishsoapbox.com/cdn/shop/files/PhotoRoom-20231116_094454.png?v=1700719316",
    "creatorName": "Marco Vittorio",
    "description": "A creamy, dreamy scent infused with the luxurious sweetness of vanilla, offering a comforting and indulgent experience.",
    "avatarUrl": marco,
  },
  {
    "id": 6,
    "name": "Summer Sunset",
    "imageUrl": "https://example.com/summer-sunset.jpg",
    "creatorName": "Sofia Lorenz",
    "description": "The warmth of the summer sun mingling with the cool evening breeze, this scent captures the fleeting beauty of a sunset.",
    "avatarUrl": sofia,
  },
  {
    "id": 7,
    "name": "Gardenia Whisper",
    "imageUrl": "https://example.com/gardenia-whisper.jpg",
    "creatorName": "Liam Neeson",
    "description": "A subtle, yet intoxicating scent of gardenia, evoking the serene and delicate beauty of a moonlit garden.",
    "avatarUrl": liam,
  },
  {
    "id": 8,
    "name": "Lavender Serenity",
    "imageUrl": "https://example.com/lavender-serenity.jpg",
    "creatorName": "Elena Miro",
    "description": "The calming essence of lavender, designed to soothe the mind and spirit, creating a peaceful sanctuary in scent.",
    "avatarUrl": elena,
  },
  {
    "id": 9,
    "name": "Mystic Musk",
    "imageUrl": "https://example.com/mystic-musk.jpg",
    "creatorName": "Marco Vittorio",
    "description": "A mysterious blend of musk and spices, this fragrance is an invitation to explore the depths of the unknown.",
    "avatarUrl": marco,
  },
  {
    "id": 10,
    "name": "Cherry Blossom",
    "imageUrl": "https://example.com/cherry-blossom.jpg",
    "creatorName": "Sofia Lorenz",
    "description": "A delicate floral scent capturing the fleeting beauty of cherry blossoms in bloom, symbolizing the renewal of spring.",
    "avatarUrl": sofia,
  },
  {
    "id": 11,
    "name": "Fresh Rain",
    "imageUrl": "https://example.com/fresh-rain.jpg",
    "creatorName": "Elena Miro",
    "description": "The clean, invigorating scent of rain on a spring morning, bringing a sense of renewal and clarity.",
    "avatarUrl": elena,
  },
  {
    "id": 12,
    "name": "Woodland Whisper",
    "imageUrl": "https://example.com/woodland-whisper.jpg",
    "creatorName": "Marco Vittorio",
    "description": "A secret blend that captures the essence of ancient forests, filled with the whispers of oak and pine.",
    "avatarUrl": marco,
  },
  {
    "id": 13,
    "name": "Midnight Jasmine",
    "imageUrl": "https://example.com/midnight-jasmine.jpg",
    "creatorName": "Sofia Lorenz",
    "description": "An intoxicating fragrance that blooms under the moon's glow, embodying the mystique and allure of jasmine at night.",
    "avatarUrl": sofia,
  },
  {
    "id": 14,
    "name": "Golden Amber",
    "imageUrl": "https://example.com/golden-amber.jpg",
    "creatorName": "Liam Neeson",
    "description": "Rich and warm, this scent melds the luxurious depths of amber with a hint of spice, reminiscent of ancient treasures.",
    "avatarUrl": liam,
  },
  {
    "id": 15,
    "name": "Wild Orchid",
    "imageUrl": "https://example.com/wild-orchid.jpg",
    "creatorName": "Elena Miro",
    "description": "A daring and exotic scent that captures the elusive beauty of the wild orchid, vibrant and deeply sensual.",
    "avatarUrl": elena,
  },
  {
    "id": 16,
    "name": "Sunny Citrus",
    "imageUrl": "https://example.com/sunny-citrus.jpg",
    "creatorName": "Marco Vittorio",
    "description": "A joyful burst of citrus sunshine, combining the zesty notes of orange, lemon, and lime for an uplifting sensation.",
    "avatarUrl": marco,
  },
  {
    "id": 17,
    "name": "Spicy Cinnamon",
    "imageUrl": "https://example.com/spicy-cinnamon.jpg",
    "creatorName": "Sofia Lorenz",
    "description": "Warm and comforting, this fragrance swirls with the rich, spicy essence of cinnamon, evoking cozy memories.",
    "avatarUrl": sofia,
  },
  {
    "id": 18,
    "name": "Frosty Mint",
    "imageUrl": "https://example.com/frosty-mint.jpg",
    "creatorName": "Liam Neeson",
    "description": "Cool and invigorating, this scent captures the crispness of frosty mint leaves, offering a refreshing clarity.",
    "avatarUrl": liam,
  },
  {
    "id": 19,
    "name": "Cedarwood Aura",
    "imageUrl": "https://example.com/cedarwood-aura.jpg",
    "creatorName": "Elena Miro",
    "description": "A grounding scent that embodies the strength and serenity of cedarwood, providing a comforting, earthy embrace.",
    "avatarUrl": elena,
  },
  {
    "id": 20,
    "name": "Rose Petal",
    "imageUrl": "https://example.com/rose-petal.jpg",
    "creatorName": "Marco Vittorio",
    "description": "The classic, romantic scent of delicate rose petals, capturing the essence of love and beauty in bloom.",
    "avatarUrl": marco,
  },
  {
    "id": 21,
    "name": "Gentle Breeze",
    "imageUrl": "https://example.com/gentle-breeze.jpg",
    "creatorName": "Sofia Lorenz",
    "description": "Light and airy, this fragrance whispers of a gentle breeze on a tranquil day, soothing and peaceful.",
    "avatarUrl": sofia,
  },
  /*
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
  },*/
];

export function getPerfume(id: number) {
  return perfumes.find((perfume) => perfume.id === id) || {
    id,
    name: `Perfume #${id}`,
    imageUrl: "https://example.com/earthy-essence.jpg",
    creatorName: "Unknown",
    description: "Unknown",
    avatarUrl: "https://thispersondoesnotexist.com/"
  };
}
