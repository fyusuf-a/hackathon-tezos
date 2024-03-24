export const $authFetch: typeof $fetch = function <T>(url, options) {
  const magicStore = useMagicStore();

  if (magicStore.isConnected) {
    options ||= {};
    options.headers ||= {};
    options.headers["Authorization"] = `Bearer ${magicStore.did}`;
  }

  return $fetch<T>(url, options);
} as any;
