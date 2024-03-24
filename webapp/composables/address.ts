export function toShortAddress(address: string) {
  if (!address) {
    return "???";
  }

  const prefix = address.substring(0, 2 + 4);
  const suffix = address.substring(address.length - 4, address.length);

  return `${prefix}...${suffix}`;
}
