const ProductCard = ({name, content, price, attachment, button}) => {
  return (
    <>
      <div>
        <div className="card">
          {attachment?.type === 'IMAGE' && <img src={`${process.env.REACT_APP_MEDIA_URL}/${attachment.url}`} className="card-img-top" alt={content} />}
          <div className="card-body">
            <h5 className="card-title">{name}</h5>
            <p className="card-text">Цена: {price} руб.</p>
            {button}
          </div>
        </div>
      </div>
    </>
  );
};

export default ProductCard;
