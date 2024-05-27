import React, { useState, useEffect } from 'react';
import { Dropdown } from 'primereact/dropdown';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import 'primereact/resources/themes/saga-blue/theme.css'; // Theme CSS
import 'primereact/resources/primereact.min.css'; // Core CSS
import axios from 'axios';
import { InputTextarea } from 'primereact/inputtextarea';

const NonProfits = ({ foundations, nonProfits }) => {
  const [selectedFoundation, setSelectedFoundation] = useState(null);
  const [selectedNonProfits, setSelectedNonProfits] = useState([]);
  const [message, setMessage] = useState('');
  const [displayDialog, setDisplayDialog] = useState(false);

  const sendEmailButtonEnabled = selectedNonProfits.length > 0 && selectedFoundation?.id>0;

  const handleSendEmail = () => {
    // Make API call to create email data
    const data = {
      message: message,
      foundation: { id: selectedFoundation.id },
      nonProfits: selectedNonProfits.map(nonProfit => ({ id: nonProfit.id }))
    };

    axios.post('http://localhost:8080/api/emaildata', data)
      .then(response => {
        console.log('Email sent successfully:', response.data);
        setDisplayDialog(false);
        // Reset state
        setSelectedNonProfits([]);
        setMessage('');
      })
      .catch(error => {
        console.error('Error sending email:', error);
      });
  };

  return (
    <div className="p-grid">
      <div className="p-col">
        <Dropdown
          value={selectedFoundation}
          options={foundations.map(f => {
            return { name: f.email, id: f.id };
          })}
          onChange={(e) => setSelectedFoundation(e.value)}
          optionLabel="name"
          placeholder="Select Foundation"
        />
        <DataTable value={nonProfits} selectionMode="multiple" selection={selectedNonProfits} onSelectionChange={(e) => setSelectedNonProfits(e.value)}>
          <Column selectionMode="multiple" style={{ width: '3em' }} />
          <Column field="id" header="ID" />
          <Column field="name" header="Name" />
          <Column field="address" header="Address" />
          <Column field="email" header="Email" />
        </DataTable>
        <Button label="Send Email" className="p-button-primary" disabled={!sendEmailButtonEnabled} onClick={() => setDisplayDialog(true)} />
      </div>
      <Dialog visible={displayDialog} onHide={() => setDisplayDialog(false)} header="Send Email" modal>
        <div>
          <label htmlFor="message">Message:</label>
          <InputTextarea id="message" rows="5" cols="30" value={message} onChange={(e) => setMessage(e.target.value)} />
        </div>
        <div className="p-d-flex p-jc-end">
          <Button label="Cancel" className="p-button-text" onClick={() => setDisplayDialog(false)} />
          <Button label="Send" className="p-button-primary" onClick={handleSendEmail} />
        </div>
      </Dialog>
    </div>
  );
};

export default NonProfits;
