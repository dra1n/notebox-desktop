(ns notebox.fragments.logo.views)

(defn logo [_]
  {:fx/type :h-box
   :alignment :center
   :children [{:fx/type :v-box
               :style-class "logo-first"
               :children [{:fx/type :svg-path
                           :fill "#6CC5CF"
                           :scale-x 1
                           :scale-y 1
                           :content  "M9.299 31.0287C6.88205 22.7811 4.79314 15.2009 3.03221 8.288V25.1253C3.03221 26.5627 3.17032 29.0095 3.44654 32.466C3.68824 36.2305 3.80909 38.6602 3.80909 39.7553C3.80909 40.0633 3.61918 40.3286 3.23938 40.551C2.85957 40.7734 2.42798 40.8847 1.94459 40.8847C1.2195 40.8847 0.85696 40.6109 0.85696 40.0633L0.960544 38.5747C1.0296 37.4795 1.06413 36.7609 1.06413 36.4187C1.06413 34.6049 0.960545 31.7302 0.753377 27.7947C0.546209 23.7906 0.442627 21.3609 0.442627 20.5053V13.9347C0.442627 12.8738 0.494418 11.2996 0.598002 9.212C0.736114 7.39821 0.805169 5.82401 0.805169 4.48933V4.18133C0.805169 2.47021 1.18497 1.61467 1.94459 1.61467C2.39345 1.61467 2.96315 2.36755 3.65371 3.87333C4.79313 6.33734 6.24328 10.752 8.00421 17.1173C9.00552 20.8134 10.1795 25.5702 11.526 31.388C11.4915 31.2511 11.7677 32.4318 12.3547 34.93L12.4065 35.1867C12.4065 31.8671 12.441 28.8898 12.5101 26.2547L12.6137 17.3227V2.128C12.7518 1.27244 13.3042 0.844666 14.271 0.844666C15.2033 0.844666 15.6694 1.51199 15.6694 2.84667V3.15467C15.4622 3.56533 15.3586 4.06155 15.3586 4.64333C15.3586 6.7309 15.3068 9.87931 15.2033 14.0887C15.0997 18.3322 15.0479 21.5149 15.0479 23.6367V39.2933C15.0479 39.8409 14.9011 40.3628 14.6076 40.859C14.3142 41.3552 13.9603 41.6033 13.5459 41.6033C12.8554 41.6033 11.4397 38.0785 9.299 31.0287ZM25.717 40.9617C24.9573 40.397 24.3359 39.6356 23.8525 38.6773C22.8512 36.5898 22.2297 34.314 21.988 31.85C21.6772 29.5229 21.5218 27.2813 21.5218 25.1253C21.5218 24.7147 21.5391 24.3724 21.5736 24.0987V23.1747C21.5736 23.0036 21.5564 22.918 21.5218 22.918V21.994L21.5736 18.4007C21.6082 17.3055 21.7463 15.5602 21.988 13.1647C22.1606 11.522 22.5577 9.93067 23.1792 8.39067C23.4209 7.70622 23.7316 7.18433 24.1114 6.825C24.4912 6.46566 24.9228 6.286 25.4062 6.286C26.7873 6.286 28.0303 6.53411 29.1352 7.03033C30.2401 7.52656 31.1723 8.21955 31.932 9.10933C32.6225 9.96489 33.2095 11.0172 33.6929 12.2663C34.1763 13.5154 34.5215 14.8415 34.7287 16.2447C34.9359 17.682 35.0395 19.2049 35.0395 20.8133V21.224C35.0395 22.2849 35.0567 23.2431 35.0913 24.0987L35.143 27.0247C35.143 36.8807 32.8297 41.8087 28.203 41.8087C27.3052 41.8087 26.4766 41.5263 25.717 40.9617ZM29.7049 38.9083C30.2228 38.4121 30.6199 37.7362 30.8961 36.8807C31.5522 35.1353 31.9492 33.2531 32.0873 31.234C32.26 29.4887 32.3463 27.9829 32.3463 26.7167C32.3463 25.1767 32.2254 23.1576 31.9838 20.6593C31.7766 18.0242 31.673 16.0564 31.673 14.756V13.832C31.6039 12.5315 31.1637 11.4022 30.3523 10.444C29.5409 9.48577 28.5482 9.00667 27.3743 9.00667C26.2003 9.00667 25.4235 9.67399 25.0437 11.0087C24.6984 12.0696 24.5258 13.4384 24.5258 15.1153C24.5258 15.7656 24.543 16.3131 24.5775 16.758V18.144L24.474 20.5053C24.4049 21.224 24.3704 22.0111 24.3704 22.8667C24.3704 24.7147 24.4222 26.5455 24.5258 28.3593C24.6639 30.926 25.0091 33.4242 25.5616 35.854C26.114 38.3865 26.9254 39.6527 27.9958 39.6527C28.6173 39.6527 29.187 39.4046 29.7049 38.9083ZM43.6887 40.012L43.6369 38.01C43.6023 37.4282 43.5851 36.778 43.5851 36.0593L43.6369 34.314C43.5333 33.9376 43.2743 30.1903 42.86 23.072C42.4457 15.9537 42.204 11.3851 42.1349 9.366C40.1323 9.29755 38.8548 9.09222 38.3023 8.75C37.9225 8.47622 37.7326 8.16822 37.7326 7.826C37.7326 7.55222 37.9139 7.30411 38.2764 7.08167C38.639 6.85922 39.0102 6.748 39.39 6.748C40.3567 6.748 41.5307 6.81644 42.9118 6.95333C44.2239 7.12444 45.3978 7.21 46.4336 7.21H47.2623C48.3327 7.21 48.8678 7.62066 48.8678 8.442C48.8678 8.85267 48.6952 9.18633 48.3499 9.443C48.0046 9.69967 47.6076 9.828 47.1587 9.828C46.848 9.828 46.3991 9.77667 45.8121 9.674L44.7763 9.46867L44.6727 9.982C44.6727 13.0278 44.9662 17.8873 45.5532 24.5607C46.1056 30.8234 46.3818 35.6829 46.3818 39.1393C46.3818 39.55 46.2006 39.9093 45.838 40.2173C45.4755 40.5253 45.0698 40.6793 44.6209 40.6793L43.6887 40.012ZM55.2123 41.0387C54.8498 40.7307 54.6512 40.3542 54.6167 39.9093V37.548C54.5822 37.24 54.5649 36.8807 54.5649 36.47C54.5649 35.7513 54.5131 34.6562 54.4095 33.1847L54.306 29.8993C54.306 28.2224 54.3577 25.7242 54.4613 22.4047C54.5994 19.4273 54.6685 16.9291 54.6685 14.91C54.6685 14.4309 54.6167 13.7122 54.5131 12.754C54.4095 11.7615 54.3577 11.0429 54.3577 10.598C54.3577 8.16821 55.0828 6.95333 56.533 6.95333C57.6724 6.95333 58.9931 7.17577 60.4951 7.62067C61.997 8.06556 62.748 8.596 62.748 9.212C62.748 9.55422 62.6013 9.87078 62.3078 10.1617C62.0143 10.4526 61.6604 10.6836 61.246 10.8547C59.7959 10.3413 58.4838 9.99911 57.3099 9.828C57.3444 10.3413 57.3617 11.0771 57.3617 12.0353C57.3617 13.4042 57.3444 14.6362 57.3099 15.7313C57.2063 17.374 57.1545 18.606 57.1545 19.4273L57.3099 20.2487L58.1385 20.1973C58.4148 20.1631 58.76 20.146 59.1744 20.146C60.8317 20.146 61.6604 20.6251 61.6604 21.5833C61.6604 21.9598 61.505 22.2763 61.1942 22.533C60.8835 22.7897 60.4864 22.918 60.003 22.918C59.3815 22.918 58.8982 22.8838 58.5529 22.8153H57.2581C57.3271 23.7051 57.3617 25.0055 57.3617 26.7167C57.3617 28.4278 57.3444 29.9678 57.3099 31.3367C57.2063 33.4242 57.1545 34.9813 57.1545 36.008C57.1545 37.9244 57.189 38.8827 57.2581 38.8827L58.9672 38.8313C59.5542 38.7971 60.262 38.78 61.0907 38.78C62.4027 38.78 63.0587 39.1907 63.0587 40.012C63.0587 40.4911 62.8429 40.859 62.4114 41.1157C61.9798 41.3723 61.4532 41.5007 60.8317 41.5007H56.4294C55.9806 41.5007 55.5749 41.3467 55.2123 41.0387Z"}]}
              {:fx/type :svg-path
               :fill "#A4A0A2"
               :scale-x 1
               :scale-y 1
               :content  "M69.076 39.9606L69.2832 27.3326C69.2832 25.0397 69.2314 21.5662 69.1278 16.912C69.0242 12.292 68.9724 8.81844 68.9724 6.49131V6.18331C68.9724 5.29353 69.0587 4.47221 69.2314 3.71931C69.4385 2.79531 69.991 2.33331 70.8887 2.33331C73.3057 2.33331 75.2219 3.10331 76.6376 4.64331C78.0532 6.18332 78.761 8.21952 78.761 10.752C78.761 12.292 78.3467 13.9004 77.518 15.5773C76.6894 17.2542 75.7053 18.5033 74.5659 19.3246C76.5685 20.6593 78.0446 22.379 78.9941 24.4836C79.9436 26.5883 80.4184 28.9069 80.4184 31.4393C80.4184 32.6371 80.004 34.1257 79.1754 35.9053C78.1741 38.0271 76.8102 39.55 75.0838 40.474C74.048 40.9873 72.9086 41.244 71.6656 41.244H71.4584C69.8701 41.244 69.076 40.8162 69.076 39.9606ZM74.2552 16.3473C74.7731 15.3891 75.2565 14.2255 75.7053 12.8566C75.8089 12.4802 75.9815 12.0011 76.2232 11.4193V9.62265C76.2232 8.59597 75.8434 7.60354 75.0838 6.64531C74.3242 5.68709 73.461 5.20798 72.4942 5.20798C72.0109 5.20798 71.7692 5.73842 71.7692 6.79931C71.7692 9.05799 71.8037 11.0771 71.8727 12.8566L71.9763 18.5546C72.8741 18.3493 73.6337 17.6135 74.2552 16.3473ZM76.2232 36.2646C77.2246 34.9642 77.7252 33.4071 77.7252 31.5933C77.7252 30.0191 77.5957 28.5732 77.3368 27.2556C77.0778 25.9381 76.7412 24.9371 76.3268 24.2526C75.947 23.6024 75.5672 23.0549 75.1874 22.61C74.8076 22.1651 74.4796 21.8742 74.2034 21.7373L73.789 21.5833H71.9245C72.0972 25.5189 72.1835 31.1826 72.1835 38.5746C73.8754 38.3351 75.2219 37.5651 76.2232 36.2646ZM88.9122 40.9616C88.1526 40.397 87.5311 39.6355 87.0477 38.6773C86.0464 36.5897 85.4249 34.314 85.1832 31.85C84.8725 29.5229 84.7171 27.2813 84.7171 25.1253C84.7171 24.7146 84.7343 24.3724 84.7689 24.0986V23.1746C84.7689 23.0035 84.7516 22.918 84.7171 22.918V21.994L84.7689 18.4006C84.8034 17.3055 84.9415 15.5602 85.1832 13.1646C85.3558 11.522 85.7529 9.93065 86.3744 8.39065C86.6161 7.7062 86.9269 7.18432 87.3067 6.82498C87.6865 6.46564 88.1181 6.28598 88.6015 6.28598C89.9826 6.28598 91.2256 6.53409 92.3305 7.03031C93.4353 7.52654 94.3676 8.21953 95.1272 9.10931C95.8178 9.96487 96.4047 11.0172 96.8881 12.2663C97.3715 13.5154 97.7168 14.8415 97.924 16.2446C98.1311 17.682 98.2347 19.2049 98.2347 20.8133V21.224C98.2347 22.2849 98.252 23.2431 98.2865 24.0986L98.3383 27.0246C98.3383 36.8807 96.025 41.8086 91.3982 41.8086C90.5005 41.8086 89.6718 41.5263 88.9122 40.9616ZM92.9002 38.9083C93.4181 38.4121 93.8152 37.7362 94.0914 36.8806C94.7474 35.1353 95.1445 33.2531 95.2826 31.234C95.4552 29.4886 95.5415 27.9829 95.5415 26.7166C95.5415 25.1766 95.4207 23.1575 95.179 20.6593C94.9718 18.0242 94.8682 16.0564 94.8682 14.756V13.832C94.7992 12.5315 94.359 11.4022 93.5476 10.444C92.7362 9.48575 91.7435 9.00665 90.5695 9.00665C89.3956 9.00665 88.6187 9.67397 88.2389 11.0086C87.8936 12.0695 87.721 13.4384 87.721 15.1153C87.721 15.7655 87.7383 16.3131 87.7728 16.758V18.144L87.6692 20.5053C87.6001 21.224 87.5656 22.0111 87.5656 22.8666C87.5656 24.7147 87.6174 26.5455 87.721 28.3593C87.8591 30.926 88.2044 33.4242 88.7568 35.854C89.3093 38.3864 90.1207 39.6526 91.191 39.6526C91.8125 39.6526 92.3822 39.4045 92.9002 38.9083ZM103.31 40.6793C103 40.474 102.844 40.1831 102.844 39.8066C102.844 38.7115 102.991 37.4881 103.284 36.1363C103.578 34.7845 104.001 33.1675 104.553 31.2853L105.071 29.2833C105.555 27.3669 105.796 25.7071 105.796 24.304C105.796 23.3115 105.356 20.8647 104.476 16.9633C103.595 13.062 102.965 10.8375 102.585 10.29V9.72531C102.24 9.41731 102.067 8.98954 102.067 8.44198C102.067 8.13398 102.223 7.88587 102.533 7.69765C102.844 7.50942 103.207 7.41531 103.621 7.41531C104.035 7.41531 104.381 7.7062 104.657 8.28798C105.175 9.24621 105.693 11.0086 106.211 13.5753C106.487 14.7389 106.677 15.7142 106.78 16.5013L107.143 18.4006L107.35 17.1686L107.505 16.142C107.644 15.3206 107.73 14.7731 107.764 14.4993C107.902 13.5753 107.972 12.6855 107.972 11.83V10.136C107.972 9.17775 108.136 8.44198 108.464 7.92865C108.792 7.41531 109.404 7.03887 110.302 6.79931L111.234 8.03131C110.993 8.40776 110.794 8.86976 110.639 9.41731C110.483 9.96487 110.406 10.444 110.406 10.8546C110.371 11.0942 110.354 11.6246 110.354 12.446V13.0106C109.94 14.1058 109.508 16.0136 109.059 18.7343C108.61 21.455 108.386 23.4313 108.386 24.6633C108.386 25.7242 108.619 27.0674 109.085 28.693C109.551 30.3185 110.25 32.5686 111.183 35.4433C111.804 37.2913 112.115 38.6431 112.115 39.4986C112.115 39.8409 111.977 40.1403 111.701 40.397C111.424 40.6537 111.096 40.782 110.716 40.782C110.13 40.782 109.612 40.166 109.163 38.934C108.714 37.9073 108.334 36.6069 108.023 35.0326C107.609 33.1162 107.367 31.8329 107.298 31.1826L107.039 30.002L106.573 33.8006C106.366 35.6829 106.09 37.2571 105.744 38.5233V39.088C105.744 40.3542 105.296 40.9873 104.398 40.9873C103.984 40.9873 103.621 40.8847 103.31 40.6793Z"}]})