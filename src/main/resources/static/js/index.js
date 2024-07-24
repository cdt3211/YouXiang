document.addEventListener('DOMContentLoaded', (event) => {
    function loadPosts(category_id) {
        localStorage.setItem('selectedCategoryId', category_id);

        fetch('/index/products/' + category_id)
            .then(response => response.json())
            .then(products => {
                let cards = document.getElementById('cards');
                if (!cards) {
                    console.error('Element with id "cards" not found.');
                    return;
                }
                cards.innerHTML = '';
                products.forEach(product => {
                    let card = document.createElement('a');
                    card.href = `/index/product/${product.product_id}`;
                    card.className = 'card';
                    card.innerHTML = `
                        <div class="card-cover">
                            <img src="${product.image}" alt="${product.name}">
                        </div>
                        <div class="card-info">
                            <div class="product-name">${product.name}</div>
                            <p>${product.description}</p>
                            <div class="author-wrapper">
                                <div class="likes">
                                    <i class="far fa-star"></i>
                                    <span>${product.collected}</span>
                                </div>
                                <div>
                                    <i class="fas fa-feather-alt"></i>
                                    <span>${product.postnumbers}</span>
                                </div>
                            </div>
                        </div>
                    `;
                    cards.appendChild(card);
                });
            })
            .catch(error => console.error('Error:', error));
    }

    function loadAllPosts() {
        localStorage.removeItem('selectedCategoryId');

        fetch('/index/posts/all')
            .then(response => response.json())
            .then(posts => {
                let cards = document.getElementById('cards');
                if (!cards) {
                    console.error('Element with id "cards" not found.');
                    return;
                }
                cards.innerHTML = '';
                posts.forEach(post => {
                    let card = document.createElement('a');
                    card.href = `/index/post/${post.post_id}`;
                    card.className = 'card';
                    card.innerHTML = `
                        <div class="card-cover">
                            <img src="${post.cover_image}" alt="${post.title}">
                        </div>
                        <div class="card-info">
                            <h3><span class="post-title">${post.title}</span></h3>
                            <p>${post.description}</p>
                            <div class="author-wrapper">
                                <div class="author">
                                    <img src="${post.user.avatar}" alt="Avatar" class="author-avatar">
                                    <span>${post.user.username}</span>
                                </div>
                                <div class="likes">
                                    <i class="far fa-heart"></i>
                                    <span>${post.likes}</span>
                                </div>
                            </div>
                        </div>
                    `;
                    cards.appendChild(card);
                });
            })
            .catch(error => console.error('Error:', error));
    }

    function search(searchStr) {
        fetch('/searchProductsIndex?searchStr=' + searchStr)
            .then(response => response.json())
            .then(products => {
                displayResults(products);
            })
            .catch(error => console.error('Error:', error));

        fetch('/searchPostsIndex?searchStr=' + searchStr)
            .then(response => response.json())
            .then(posts => {
                displayResults(posts);
            })
            .catch(error => console.error('Error:', error));
    }

    function displayResults(results) {
        let cards = document.getElementById('cards');
        if (!cards) {
            console.error('Element with id "cards" not found.');
            return;
        }
        cards.innerHTML = '';
        results.forEach(item => {
            let card = document.createElement('a');
            card.href = item.product_id ? `/index/product/${item.product_id}` : `/index/post/${item.post_id}`;
            card.className = 'card';
            card.innerHTML = `
                <div class="card-cover">
                    <img src="${item.image || item.cover_image}" alt="${item.name || item.title}">
                </div>
                <div class="card-info">
                    <h3><span class="post-title">${item.name || item.title}</span></h3>
                    <p>${item.description}</p>
                    <div class="author-wrapper">
                        ${item.user ? `
                            <div class="author">
                                <img src="${item.user.avatar}" alt="Avatar" class="author-avatar">
                                <span>${item.user.username}</span>
                            </div>
                        ` : ''}
                        <div class="likes">
                            <i class="far fa-heart"></i>
                            <span>${item.collected || item.likes}</span>
                        </div>
                    </div>
                </div>
            `;
            cards.appendChild(card);
        });
    }

    // Make the functions available globally
    window.loadPosts = loadPosts;
    window.loadAllPosts = loadAllPosts;

    const selectedCategoryId = localStorage.getItem('selectedCategoryId');
    if (selectedCategoryId) {
        loadPosts(selectedCategoryId);
    } else {
        loadAllPosts();
    }

    // 获取搜索输入框和搜索按钮的引用
    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');

    // 为搜索按钮添加点击事件监听器
    searchButton.addEventListener('click', () => {
        const searchStr = searchInput.value.trim();
        if (searchStr) {
            search(searchStr);
        }
    });

    // 获取所有的li元素
    let liElements = document.querySelectorAll('.sidebar ul li');

    // 为每个li元素添加点击事件
    liElements.forEach((li) => {
        li.addEventListener('click', function() {
            // 将所有li元素的背景色设置为默认颜色
            liElements.forEach((li) => {
                li.style.backgroundColor = '';
            });

            // 将被点击的li元素的背景色设置为灰色
            this.style.backgroundColor = '#f5f5f5';
        });
    });
});
