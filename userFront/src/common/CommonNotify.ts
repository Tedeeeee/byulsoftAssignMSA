import { useQuasar } from 'quasar';

export const useNotifications = () => {
  const $q = useQuasar();

  const positiveNotify = (message: string) => {
    $q.notify({
      type: 'positive',
      message: message,
      position: 'top',
      timeout: 2000
    });
  };

  const negativeNotify = (message: string) => {
    $q.notify({
      type: 'negative',
      message: message,
      position: 'top',
      timeout: 2000
    });
  };

  return { positiveNotify, negativeNotify };
};
